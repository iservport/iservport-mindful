package com.iservport.install.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.helianto.core.domain.KeyType;
import org.helianto.core.domain.Operator;
import org.helianto.core.repository.KeyTypeRepository;
import org.helianto.core.repository.OperatorRepository;
import org.helianto.install.service.EntityInstallStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Key type install service.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class KeyTypeInstallService 
	implements InitializingBean
{

	private static final Logger logger = LoggerFactory.getLogger(KeyTypeInstallService.class);
	
	private static final String KEY_TYPE_PATH_FILE = "/META-INF/data/keys/";
	
	@Inject
	private Environment env;
		
	@Inject
	private KeyTypeRepository keyTypeRepository;

	@Inject
	private OperatorRepository operatorRepository;
	
	/**
	 * Brasil.
	 */
	protected String getDefaultCountry() {
		return "1058";
	}
	
	/**
	 * Estados são lidos a partir deste arquivo.
	 */
	protected String getDefaultStateFile() {
		return "states-1058.xml";
	}
	
	/**
	 * Senha inicial.
	 */
	protected String getInitialSecret() {
		return "123rooT#";
	}
	
	/**
	 * Cria tipos de chave.
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
     */
	public void afterPropertiesSet() throws JsonParseException, JsonMappingException, IOException{
		String contextName = env.getProperty("helianto.defaultContextName");
		logger.debug("Creating Key types for {}.", contextName);
		ClassPathResource resource =  new ClassPathResource(KEY_TYPE_PATH_FILE+"key-types.json");
		ObjectMapper mapper = new ObjectMapper(
				new JsonFactory()
				.configure(Feature.ALLOW_COMMENTS, true))
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<KeyType> user = 
				mapper.readValue(getFile(resource.getInputStream()), TypeFactory.defaultInstance().constructCollectionType(List.class,
				KeyType.class));
		Operator operator = operatorRepository.findByOperatorName(contextName);
		for (KeyType keyType : user) {
			logger.debug("Search ketType to context {} with keyCode {}.", contextName, keyType.getKeyCode());
			KeyType exists = keyTypeRepository.findByOperator_operatorNameAndKeyCode(contextName, keyType.getKeyCode());
			if(exists==null){
				exists = new KeyType(operator,  keyType.getKeyCode());
			}
			//update
			if(exists!=null){
				exists.setKeyCode(keyType.getKeyCode());
				exists.setKeyGroup(keyType.getKeyGroup());
				exists.setKeyName(keyType.getKeyName());
				exists.setPurpose(keyType.getPurpose());
				exists.setSynonyms(keyType.getSynonyms());
				keyTypeRepository.saveAndFlush(exists);
				logger.debug("update ketType to context {} with keyCode {} to {} .", contextName, exists.getKeyCode(), keyType);
			}
		}
	}

	private File getFile(InputStream inputStream) {
		OutputStream outputStream = null;
		File tempFile = null;
		try {
			tempFile = File.createTempFile("tempfile", ".tmp");
			outputStream =
					new FileOutputStream(tempFile);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return tempFile;
	}
}

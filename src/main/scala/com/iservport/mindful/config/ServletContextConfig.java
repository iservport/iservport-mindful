package com.iservport.mindful.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.helianto.security.resolver.CurrentUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Configuracao Java.
 * 
 * @author mauriciofernandesdecastro
 */
@Configuration
@EnableTransactionManagement
@Import({ RootContextConfig.class, MultiHttpSecurityConfig.class, SocialConfig.class })
public class ServletContextConfig extends WebMvcConfigurerAdapter {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	
	@Bean 
	public CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
		return new CurrentUserHandlerMethodArgumentResolver();
	}

	/**
	 * Registro de resolução de argumentos.
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.addAll(
			Arrays.asList(
					currentUserHandlerMethodArgumentResolver()
			)
		);
	}

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
    
	/**
	 * Formatadores.
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new StringFormatter());
		super.addFormatters(registry);
		
	}
	
//    /**
//     * User installer bean.
//     */
//    @Bean
//    public UserInstall userInstall(){
//		return new UserInstallImpl();
//    }
//    
    public class StringFormatter implements Formatter<String>{
		@Override
		public String print(String object, Locale locale) {
			return object;
		}

		@Override
		public String parse(String text, Locale locale) throws ParseException {
			return new String(text);
		}
    }
    
	/**
	 * Jackson json converter.
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converter.setObjectMapper(mapper);
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,APPLICATION_JSON_UTF8));
		return converter;
	}
    
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//para converter String direto pra json
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		stringConverter.setSupportedMediaTypes(Arrays.asList( //
				MediaType.TEXT_PLAIN, //
				MediaType.TEXT_HTML, //
				MediaType.APPLICATION_JSON));
		//Converter byte[] para imagem
		ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
		arrayHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.IMAGE_JPEG
		, MediaType.IMAGE_PNG
		, MediaType.IMAGE_GIF));
		
		converters.add(arrayHttpMessageConverter);
		converters.add(stringConverter);
		converters.add(mappingJackson2HttpMessageConverter());
		super.configureMessageConverters(converters);
    }
	
    
    /**
	 * Commons multipart resolver.
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(10000000);
		return resolver;
	}

}

package com.iservport.mindful.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

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
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;

/**
 * Configuracao Java.
 * 
 * @author mauriciofernandesdecastro
 */
@Configuration
@EnableTransactionManagement
@Import({ RootContextConfig.class, SecurityWebConfig.class, SocialConfig.class })
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
	
	/**
	 * Freemarker configurer.
	 * 
	 * @throws TemplateException 
	 * @throws IOException 
	 */
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setPreferFileSystemAccess(false);
		configurer.setTemplateLoaderPaths(
				new String[] {"/WEB-INF/classes/freemarker/"
						,"/WEB-INF/freemarker/"} );
		Properties props = new Properties();
		props.put("default_encoding", "utf-8");
		props.put("number_format", "computer");
		props.put("whitespace_stripping", "true");
		configurer.setFreemarkerSettings(props);
		return configurer;
	}
	
	/**
	 * Freemarker view resolver.
	 */
	public ViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setExposeSpringMacroHelpers(true);
		resolver.setCache(true);
		resolver.setPrefix("");
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html;charset=iso-8859-1");
		return resolver;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(Arrays.asList(freeMarkerViewResolver()));
		return resolver;
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

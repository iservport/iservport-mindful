package com.iservport.mindful.config;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.iservport.install.service.DefaultInstallStrategy;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.helianto.core.config.HeliantoServiceConfig;
import org.helianto.install.service.EntityInstallStrategy;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

/**
 * Configurações Java em geral.
 * 
 * @author mauriciofernandesdecastro
 */
@Configuration
@EnableWebMvc
@Import({HeliantoServiceConfig.class})
@ComponentScan(
		basePackages = {
				"com.iservport.*.repository"
				, "com.iservport.*.service"
				, "com.iservport.*.controller"
		})
@EnableJpaRepositories(
		basePackages={
				"com.iservport.*.repository"
		})
public class RootContextConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setRemoveSemicolonContent(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Md5PasswordEncoder notificationEncoder() {
		return new Md5PasswordEncoder();
	}

	@Bean(name = "defaultInstallStrategy")
	public EntityInstallStrategy entityInstallStrategy() {
		return new DefaultInstallStrategy();
	}

	/**
	 * Registra um interceptador para troca de Locale.
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("siteLocale");
		registry.addInterceptor(localeInterceptor);
	}

	/**
	 * Cookie locale resolver.
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
}

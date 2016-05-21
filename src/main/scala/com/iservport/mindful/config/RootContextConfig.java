package com.iservport.mindful.config;

import org.helianto.core.config.HeliantoConfig;
import org.helianto.core.service.BootstrapInstaller;
import org.helianto.install.service.EntityInstallStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.util.UrlPathHelper;

/**
 * Configurações Java em geral.
 * 
 * @author mauriciofernandesdecastro
 */
@Configuration
@EnableWebMvc
@Import({HeliantoConfig.class})
@ComponentScan(
		basePackages = {
				"com.iservport.*.service"
				, "com.iservport.*.repository"
				, "com.iservport.*.controller"
				, "org.helianto.*.service"
				, "org.helianto.*.repository"
				, "org.helianto.*.controller"
		})
@EnableJpaRepositories(
		basePackages={
				"com.iservport.*.repository"
				, "org.helianto.*.repository"
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
	public BootstrapInstaller bootstrapInstaller() {
		return new BootstrapInstaller();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Md5PasswordEncoder notificationEncoder() {
		return new Md5PasswordEncoder();
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

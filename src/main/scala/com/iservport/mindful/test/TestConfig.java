package com.iservport.home.test;

import com.iservport.mindful.config.MultiHttpSecurityConfig;
import com.iservport.mindful.config.OAuth2ClientConfig;
import org.helianto.core.config.HeliantoServiceConfig;
import org.helianto.core.test.TestDataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Configuração de teste.
 *
 * @author mauriciofernandesdecastro
 *
 */
@Configuration
@Import({TestDataSourceConfig.class
		, HeliantoServiceConfig.class
		, MultiHttpSecurityConfig.class
		, OAuth2ClientConfig.class})
@ComponentScan(
		basePackages = {"com.iservport.*.controller"})
@PropertySource("classpath:/test.properties")
public class TestConfig {

	@Bean
	public Validator validator() {
		return new Validator() {

			@Override
			public void validate(Object target, Errors errors) {
			}

			@Override
			public boolean supports(Class<?> clazz) {
				return true;
			}
		};
	}

}

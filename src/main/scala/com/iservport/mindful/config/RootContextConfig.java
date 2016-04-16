package com.iservport.mindful.config;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.helianto.core.config.HeliantoServiceConfig;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;

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
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JpaVendorAdapter vendorAdapter;
	
	/**
	 * Substitui a configuração original do <code>EntityManagerFactory</code>
	 * para incluir novos pacotes onde pesquisar por entidades persistentes.
	 */
	@Bean 
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource);
		bean.setPackagesToScan("org.helianto.*.domain","com.iservport.*.domain");
		bean.setJpaVendorAdapter(vendorAdapter);
		bean.setPersistenceProvider(new HibernatePersistence());
		bean.afterPropertiesSet();
        return bean.getObject();
	}
	
	/**
	 * Para conexão com fontes de dados via JNDI.
	 * 
	 * @throws IllegalArgumentException
	 * @throws NamingException
	 */
	@Bean
	public Object jndiObjectFactoryBean() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean jndiFactory = new JndiObjectFactoryBean();
		jndiFactory.setJndiName("jdbc/iservportDB");
		jndiFactory.setResourceRef(true);
		jndiFactory.afterPropertiesSet();
		return jndiFactory.getObject();
	}
	
	/**
	 * JNDI data source.
	 * 
	 * @throws NamingException 
	 * @throws IllegalArgumentException 
	 */
	@Bean
	public DataSource dataSource() throws IllegalArgumentException, NamingException {
		return (DataSource) jndiObjectFactoryBean();
	}
	
	/**
	 * Personaliza módulo Hibernate para Jackson.
	 */
	@Bean
	public Hibernate3Module iservportModule() {
		return new Hibernate3Module() {
			
		    @Override public void setupModule(SetupContext context) {
		    	super.setupModule(context);
		    	disable(Feature.FORCE_LAZY_LOADING);
		    }			
		};
	}
	
	/**
	 * Para direcionamento de recursos estáticos.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/META-INF/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/META-INF/fonts/**").setCachePeriod(31556926);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/META-INF/images/**").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/META-INF/js/**").setCachePeriod(31556926);
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/**").setCachePeriod(31556926);
	}	                    
	
	/**
	 * Para codificação de senhas.
	 */
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

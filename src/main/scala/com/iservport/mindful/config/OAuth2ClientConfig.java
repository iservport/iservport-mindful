package com.iservport.mindful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ReconnectFilter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.web.DisconnectController;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Social configuration.
 * 
 * @author mauriciofernandesdecastro
 */
@Configuration
@EnableSocial
public class OAuth2ClientConfig
		implements SocialConfigurer
{

	@Inject
	private DataSource dataSource;
	
	@Inject
	private UserSocialSignInService userSocialSignInService;

	/**
	 * By default, provide connection to Facebook.
	 */
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		String facebookAppKey = env.getProperty("facebook.appKey", "");
		String facebookAppSecret = env.getProperty("facebook.appSecret", "");
		cfConfig.addConnectionFactory(new FacebookConnectionFactory(facebookAppKey, facebookAppSecret));
	}

	/**
	 * User id extractor.
	 * 
	 * Uses Spring Security Authentication principal to extract the user id. 
	 */
	public UserIdSource getUserIdSource() {
		return new UserIdSource() {
			@Override
			public String getUserId() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};
	}

	/**
	 * Singleton data access object providing access to connections across all users.
	 */
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		repository.setTablePrefix("core_");
		return repository;
	}
	
	/**
	 * Facebook proxied api.
	 * 
	 * @param repository
	 */
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}
	
	@Bean
	public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}

	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new SimpleSignInAdapter(new HttpSessionRequestCache(), userSocialSignInService));
	}
	
	@Bean
	public UserSocialSignInService userSocialSignInService() {
		return new UserSocialSignInService();
	}
	
	@Bean
	public DisconnectController disconnectController(UsersConnectionRepository usersConnectionRepository, Environment env) {
		return new DisconnectController(usersConnectionRepository, env.getProperty("facebook.appSecret"));
	}

	@Bean
	public ReconnectFilter apiExceptionHandler(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
		return new ReconnectFilter(usersConnectionRepository, userIdSource);
	}

}

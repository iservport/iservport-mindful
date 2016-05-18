/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iservport.mindful.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;


/**
 * Multi Http Spring Security Configuration.
 * 
 * @author mauriciofernandesdecastro
 */
@Configuration
@EnableWebSecurity // substititui definições de SpringBoot
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MultiHttpSecurityConfig 
	extends WebSecurityConfigurerAdapter
{
	
	public static int REMEMBER_ME_DEFAULT_DURATION = 14*24*60*60; // duas semanas

	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Inject
	private UserDetailsService userDetailsService;
	
	@Inject
	private Environment env;

    @Inject
    private DataSource dataSource;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    }

	/**
	 * External configuration class.
	 *
	 * @author mauriciofernandesdecastro
	 */
	@Configuration
	@Order(1)
	public static class ExternalWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
                .antMatcher("/external/**").antMatcher("/h2-console/**")
                .authorizeRequests()
                .anyRequest().permitAll().and().csrf().disable().headers().frameOptions().disable();
		}
	}

	/**
	 * Error configuration class.
	 *
	 * @author mauriciofernandesdecastro
	 */
	@Configuration
	@Order(2)
	public static class ErrorWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
					.antMatcher("/error/**")
                    .antMatcher("/h2-console/**")
					.authorizeRequests()
					.anyRequest().permitAll().and().csrf().disable().headers().frameOptions().disable();
		}
	}

//	/**
//	 * API configuration class.
//	 * 
//	 * @author mauriciofernandesdecastro
//	 */
//    @Configuration
//    @Order(2)                                                        
//    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//            .antMatcher("/api/**")                               
//            .authorizeRequests()
//                .anyRequest().hasRole("USER")
//                .and().httpBasic()
//                .and().csrf().disable();
//        }
//    }
//
    /**
     * Web configuration class.
     * 
     * @author mauriciofernandesdecastro
     */
    @Configuration
    @Order(10)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Inject
        private AuthenticationFailureHandler authenticationFailureHandler;

        @Inject
        private PersistentTokenRepository persistentTokenRepository;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            internalConfigure(http
                    , authenticationFailureHandler
                    , persistentTokenRepository);
        }
        
    }

    /**
     * Common login configuration.
     *
     * @param http
     * @param authenticationFailureHandler
     * @param persistentTokenRepository
     */
    protected static void internalConfigure(HttpSecurity http
            , AuthenticationFailureHandler authenticationFailureHandler
            , PersistentTokenRepository persistentTokenRepository) throws Exception
    {
        http
            //Configures url based authorization
                .authorizeRequests()
                .antMatchers("/**").permitAll()
            // form login
            .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=bad_credentials")
                .failureHandler(authenticationFailureHandler)
//            // secure channel
//            .and()
//                .requiresChannel().anyRequest().requiresSecure()
            //habilita csrf
            .and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .ignoringAntMatchers("/app/content/upload")
            //habilita remember-me
            .and()
                .rememberMe().tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(REMEMBER_ME_DEFAULT_DURATION)
            //Configures the logout function
            .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .deleteCookies("remember-me")
                .deleteCookies("X-XSRF-TOKEN")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
        ;

    }

    /**
     * CSRF header filter.
     */
    public static Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                        .getName());
                if (csrf != null) {
                    Cookie cookie = new Cookie("XSRF-TOKEN", csrf.getToken());
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    /**
     * CSRF token repository.
     */
    public static CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    /**
     * Persistent token repository.
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }


    @Bean @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler 
                savedRequestAwareAuthenticationSuccessHandler() {
 
               SavedRequestAwareAuthenticationSuccessHandler auth 
                    = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}	;

    /**
     * Intercepta erros de autenticação.
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                    throws IOException, ServletException {
                String exceptionToken = exception.getMessage().trim().toLowerCase().replace(" ", "");
                int type = 0;
                if (exceptionToken.contains("badcredentials")) {
                    type = 1;
                }
                else if (exceptionToken.contains("unabletoextractvaliduser")) {
                    type = 2;
                }
                response.sendRedirect(request.getContextPath() + "/login/error?type="+type);
            }
        };
    }

    /**
	 * Criptografia.
	 */
    @Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.queryableText(env.getProperty("security.encryptPassword", "password"), env.getProperty("security.encryptSalt", "00"));
	}
    
}

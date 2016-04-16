package com.iservport.mindful.config;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * Automatically register the springSecurityFilterChain Filter for every URL.
 * 
 * @author mauriciofernandesdecastro
 */
public class SecurityWebApplicationInitializer 
	extends AbstractSecurityWebApplicationInitializer
{
	
	@Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
    }
	
}

package com.iservport.mindful.social;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe auxiliar para sign in.
 * 
 * @author mauriciofernandesdecastro
 */
public class SignInUtils {
	
	/**
	 * Programmatically signs in the user with the given the user details.
	 * 
	 * @param userDetails
	 */
	public static void signin(UserDetails userDetails) {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));	
	}
	
}

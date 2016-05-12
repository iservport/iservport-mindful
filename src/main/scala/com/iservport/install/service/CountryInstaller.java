package com.iservport.install.service;

import org.helianto.core.domain.Country;
import org.helianto.core.domain.Operator;

/**
 * Country installer interface.
 * 
 * @author mauriciofernandesdecastro
 */
public interface CountryInstaller {

	static final String DEFAULT_COUNTRY_FILE = "countries.xml";
	
	/**
	 * Do install.
	 * 
	 * @param context
	 */
	Country installCountries(Operator context);
	
}

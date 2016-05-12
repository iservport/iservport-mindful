package com.iservport.install.service;


import org.helianto.core.domain.City;
import org.helianto.core.domain.Country;
import org.helianto.core.domain.Operator;

/**
 * State and city installer interface.
 * 
 * @author mauriciofernandesdecastro
 */
public interface CityInstaller {

	/**
	 * Do install.
	 * 
	 * @param context
	 */
	City installStatesAndCities(Operator context, Country country);
	
}

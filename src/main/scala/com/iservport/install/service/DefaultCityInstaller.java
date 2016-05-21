package com.iservport.install.service;

import org.helianto.core.domain.City;
import org.helianto.core.domain.Country;
import org.helianto.core.domain.Operator;
import org.helianto.core.domain.State;
import org.helianto.core.install.CityInstaller;
import org.helianto.core.repository.CityRepository;
import org.helianto.core.repository.StateRepository;
import org.helianto.install.service.CityParser;
import org.helianto.install.service.StateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Default state and city installer.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class DefaultCityInstaller  implements CityInstaller {

	private static final Logger logger = LoggerFactory.getLogger(DefaultCityInstaller.class);
	
	@Inject
	private Environment env;
	
	@Inject
	private StateRepository stateRepository;
	
	@Inject
	private CityRepository cityRepository;
	
	@Inject
	private StateParser stateParser;
	
	@Inject
	private CityParser cityParser;
	
	/**
	 * States will be read from this file.
	 */
	protected String getDefaultStateFile() {
		return null;
	};
	
	/**
	 * Install states and cities, return the root city.
	 * 
	 * @param context
	 * @param country
	 */
	public City installStatesAndCities(Operator context, Country country) {
		String contextDataPath = env.getProperty("helianto.contextDataPath", "/META-INF/data/");
		String stateFile = env.getProperty("helianto.stateFile", getDefaultStateFile());
		String rootEntityStateCode = env.getRequiredProperty("helianto.rootEntityStateCode");
		String rootEntityCityCode = env.getRequiredProperty("helianto.rootEntityCityCode");
		
		if (country==null) {
			throw new IllegalArgumentException("Please, provide required country to allow for default city resolution.");
		}
		
		// States
		Resource stateResource = new ClassPathResource(contextDataPath+stateFile);
		List<State> states = stateParser.parseStates(context, country, stateResource);
		List<State> managedStates = stateRepository.save(states);
		logger.info("Saved {} states.", managedStates.size());
		State state = stateRepository.findByContextAndStateCode(context, rootEntityStateCode);

		// Cities
		if (state==null) {
			throw new IllegalArgumentException("Please, provide required sate to allow for default city resolution.");
		}
		
		try {
			for (State s: managedStates) {
				Resource cityResource = new ClassPathResource(resolveCityDataPath(country, s));
				List<City> cities = cityParser.parseCities(context, s, cityResource);
				cityRepository.save(cities);
				logger.info("Saved {} cities.", cities.size());	
			}
		} catch (Exception e) {
			logger.info("Error saving cities.");	
		}
		
		City city = cityRepository.findByContextAndCityCode(context, rootEntityCityCode);
		if(city==null){
			throw new IllegalArgumentException("Please, provide required data to allow for default city resolution.");
		}
		return city;
		
	}
	
	/**
	 * Convenient to resolve city files location.
	 * 
	 * @param country
	 * @param state
	 */
	protected String resolveCityDataPath(Country country, State state) {
		String contextDataPath = env.getProperty("helianto.contextDataPath", "/META-INF/data/");
		return contextDataPath+country.getCountryCode()+"/cities-"+state.getStateCode()+".xml";
	}
	
}

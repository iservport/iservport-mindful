package com.iservport.install.service;

import org.helianto.core.domain.Country;
import org.helianto.core.domain.Operator;
import org.helianto.core.install.CountryInstaller;
import org.helianto.core.repository.CountryRepository;
import org.helianto.install.service.CountryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Default country installer.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class DefaultCountryInstaller implements CountryInstaller {

	private static final Logger logger = LoggerFactory.getLogger(DefaultCountryInstaller.class);
	
	@Inject
	private Environment env;
	
	@Inject
	private CountryParser countryParser;
	
	@Inject
	private CountryRepository countryRepository;
	
	/**
	 * Default country.
	 */
	protected String getDefaultCountry() {
		return "1058";
	};
	
	/**
	 * Install countries, return the root country.
	 * 
	 * @param context
	 */
	public Country installCountries(Operator context) {
		String contextDataPath = env.getProperty("helianto.contextDataPath", "/META-INF/data/");
		String countryFile = env.getProperty("helianto.countryFile", DEFAULT_COUNTRY_FILE);
		String defaultCountry = env.getProperty("helianto.defaultCountry", getDefaultCountry());
		
		if (defaultCountry==null) {
			return countryRepository.saveAndFlush(new Country(context, "_NO", "NO_COUNTRY"));
		}
		// All countries
		Resource countryResource = new ClassPathResource(contextDataPath+countryFile);
		List<Country> countries = countryParser.parseCountries(context, countryResource);
		List<Country> managedCountries = countryRepository.save(countries);
		logger.info("Saved {} countries.", managedCountries.size());
		
		// Our country
		Country country = countryRepository.findByOperatorAndCountryCode(context, defaultCountry);
		return country;
	}
	
}

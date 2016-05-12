package com.iservport.install.service;

import org.helianto.core.domain.Country;
import org.helianto.core.domain.Operator;
import org.helianto.install.service.CountryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * XML country parser.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class XmlCountryParser implements CountryParser {

	private static final Logger logger = LoggerFactory.getLogger(XmlCountryParser.class);
	
	/**
	 * Do parse.
	 * 
	 * @param context
	 * @param rs
	 */
	public List<Country> parseCountries(Operator context, Resource rs) {
		List<Country> countryList = new ArrayList<>();
		logger.debug("About to read country list from {}.", rs);
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(rs.getInputStream());
			while (true) {
			    int event = parser.next();
			    if (event == XMLStreamConstants.END_DOCUMENT) {
			       parser.close();
			       break;
			    }
			    if (event == XMLStreamConstants.START_ELEMENT && parser.getLocalName().equals("country")) {
			    	String countryCode = parser.getAttributeValue("", "countryCode");
			    	String name = parser.getAttributeValue("", "countryName").concat("                                ").substring(0, 32).trim();
			    	Country country = new Country(context, countryCode, name);
			    	countryList.add(country);
			    	logger.debug("Added {} to country list.", country);
			    }
			}
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException("Unable to parse country data file", e);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to read from country data file", e);
		}
		return countryList;
	}

}

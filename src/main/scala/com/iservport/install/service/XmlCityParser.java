package com.iservport.install.service;

import org.helianto.core.domain.City;
import org.helianto.core.domain.Operator;
import org.helianto.core.domain.State;
import org.helianto.install.service.CityParser;
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
 * XML city parser.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class XmlCityParser implements CityParser {

	private static final Logger logger = LoggerFactory.getLogger(XmlCityParser.class);
	
	/**
	 * Do parse.
	 * 
	 * @param context
	 * @param rs
	 */
	public List<City> parseCities(Operator context, State state, Resource rs){
		List<City> cityList = new ArrayList<>();
		logger.debug("About to read city list from {}.", rs);
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(rs.getInputStream());
			while (true) {
			    int event = parser.next();
			    if (event == XMLStreamConstants.END_DOCUMENT) {
			       parser.close();
			       break;
			    }
			    if (event == XMLStreamConstants.START_ELEMENT && parser.getLocalName().equals("city")) {
			    	String cityCode = parser.getAttributeValue("", "cityCode");
			    	City city = new City(state, cityCode, parser.getAttributeValue("", "cityName"));
			    	city.setPriority('0');
			    	cityList.add(city);
			    	logger.debug("Added {} to city list.", city);
			    }
			}
		} catch (XMLStreamException e) {
			throw new IllegalArgumentException("Unable to parse city data file", e);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to read from city data file", e);
		}
		return cityList;
	
	}
	
}

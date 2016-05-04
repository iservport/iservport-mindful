package com.iservport.mindful.internal;

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Signup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity prototype generation by domain.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class DefaultInstallStrategy 
	extends AbstractEntityInstallStrategy
{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultInstallStrategy.class);
	
	/**
	 * Do generate.
	 * 
	 * @param params
	 */
	public List<Entity> generateEntityPrototypes(Object... params) {
		if (params!=null && params.length>0 && params[0] instanceof Signup) {
			Signup signup =	(Signup) params[0];
			List<Entity> entityList = new ArrayList<>();
			Entity prototype = createPrototype(signup.getDomain(), signup);
			logger.info("Created prototype from domain {}.", prototype);
			entityList.add(prototype);
			return entityList;
		}
		throw new IllegalArgumentException("Signup required.");

	}

}

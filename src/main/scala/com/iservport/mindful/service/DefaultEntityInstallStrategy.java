package com.iservport.mindful.service;

import java.util.ArrayList;
import java.util.List;

import org.helianto.core.domain.Entity;
import org.helianto.install.service.AbstractEntityInstallStrategy;
import org.springframework.stereotype.Service;

/**
 * Entity prototype generation.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class DefaultEntityInstallStrategy 
	extends AbstractEntityInstallStrategy
{

	public List<Entity> generateEntityPrototypes(Object... params) {
		List<Entity> entityList = new ArrayList<>();
		entityList.add(createPrototype("SENADO_FEDERAL", "Senado Federal", 'C'));
		entityList.add(createPrototype("CAMARA_FEDERAL", "Câmara Federal", 'C'));
		// TODO provisoriamente o estado do Paraná e a cidade de Curitiba
		entityList.add(createPrototype("AL_PR", "Assembléia Legislativa do Estado do Paraná", 'C'));
		entityList.add(createPrototype("CM_", "Câmara de Vereadores de Curitiba", 'C'));
		return entityList;
	}
	
}

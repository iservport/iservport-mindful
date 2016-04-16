package com.iservport.mindful.service;

import javax.inject.Inject;

import org.helianto.core.repository.EntityReadAdapter;
import org.helianto.core.repository.EntityRepository;
import org.helianto.user.repository.UserReadAdapter;
import org.helianto.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Serviço para leitura em home.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class HomeQueryService {

	private static final Logger logger = LoggerFactory.getLogger(HomeQueryService.class);
	
	@Inject
	private EntityRepository entityRepository;
	
	@Inject
	private UserRepository userRepository;
	
	/**
	 * Leitura de entidade.
	 * 
	 * @param entityId
	 */
	public EntityReadAdapter entity(int entityId) {
		EntityReadAdapter adapter = entityRepository.findAdapter(entityId);
		logger.debug("Entity found: {}.", adapter);
		return adapter;
	}

	/**
	 * Leitura de usuário.
	 * 
	 * @param userId
	 */
	public UserReadAdapter user(int userId) {
		UserReadAdapter adapter = userRepository.findAdapter(userId);
		logger.debug("User found: {}.", adapter);
		return adapter;
	}

}

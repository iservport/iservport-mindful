package com.iservport.mindful.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.helianto.core.internal.KeyNameAdapter;
import org.helianto.core.internal.SimpleCounter;
import org.helianto.user.domain.UserGroup;
import org.helianto.user.repository.UserGroupRepository;
import org.helianto.user.repository.UserReadAdapter;
import org.helianto.user.repository.UserRepository;
import org.joda.time.DateMidnight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.iservport.mindful.internal.QualifierAdapter;
import com.iservport.mindful.repository.UserRoleReadAdapter;
import com.iservport.mindful.repository.UserRoleTmpRepository;
import com.iservport.mindful.repository.UserStatsRepository;
import com.iservport.mindful.repository.UserTmpRepository;

/**
 * Serviço para leitura de usuários.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class UserQueryService {

	@Inject 
	private UserGroupRepository userGroupRepository;
	
	@Inject 
	private UserRepository userRepository;
	
	@Inject 
	private UserTmpRepository userTmpRepository;
	
	@Inject 
	private UserRoleTmpRepository userRoleTmpRepository;
	
	@Inject 
	private UserStatsRepository userStatsRepository;
	
	public List<QualifierAdapter> qualifierList(int entityId) {
		
		// listamos grupos de usuários como qualificadores.
		List<UserGroup> userGroupList 
			= userGroupRepository.findByEntity_IdAndUserType(entityId, 'A', null);
		List<QualifierAdapter> qualifierList = new ArrayList<>();
		for (UserGroup userGroup: userGroupList) {
			qualifierList.add(new QualifierAdapter((KeyNameAdapter) userGroup));
		}
		
//		// realiza a contagem
//		qualifierCount(entityId, qualifierList, 0);
		
		return qualifierList;
	}
	
	/**
	 * Método auxiliar para contar os qualificadores.
	 * 
	 * @param entityId
	 * @param qualifierList
	 */
	public void qualifierCount(int entityId, List<QualifierAdapter> qualifierList, int daysToWarn) {
		
		// conta os usuários ativos agrupados por tipo
		List<SimpleCounter> counterListAll 
			= userStatsRepository.countActiveUsersGroupByType(entityId);

		// conta os usuários ativos com pendências agrupados por tipo
		List<SimpleCounter> counterListLate 
			= userStatsRepository.countUsersGroupByType(entityId
					, new DateMidnight().toDate());
		
		// para cada qualificador preenchemos as contagens
		for (QualifierAdapter qualifier: qualifierList) {
			qualifier
			.setCountItems(counterListAll)
			.setCountAlerts(counterListLate);
		}
						
	}
	
	public Page<UserRoleReadAdapter> userRole(int userId, int pageNumber) {
		// TODO conferir userRoleReadAdapter
		Page<UserRoleReadAdapter> staffMemberList = userRoleTmpRepository.findByUserId(userId, 
				new PageRequest(pageNumber, 100, Direction.ASC, "child.id"));
		return staffMemberList;
	}

	public Page<UserReadAdapter> userList(int userGroupId, String userStateKey, int pageNumber) {
		Pageable page = new PageRequest(pageNumber, 20, Direction.ASC, "userName");
		Page<UserReadAdapter> userPage = userTmpRepository.findByParent(userGroupId, userStateKey.toCharArray(), page);
		return userPage;
	}
	
}

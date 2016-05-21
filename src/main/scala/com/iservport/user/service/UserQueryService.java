package com.iservport.user.service;


import java.util.List;

import javax.inject.Inject;

import com.iservport.mindful.repository.UserStatsRepository;
import org.helianto.core.internal.QualifierAdapter;
import org.helianto.user.repository.UserGroupRepository;
import org.helianto.user.repository.UserReadAdapter;
import org.helianto.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.iservport.mindful.repository.UserRoleReadAdapter;
import com.iservport.mindful.repository.UserRoleTmpRepository;
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
		List<QualifierAdapter> qualifierList =
				org.helianto.core.internal.QualifierAdapter.qualifierAdapterList(SimpleUserKeyName.values());
		
		return qualifierList;
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

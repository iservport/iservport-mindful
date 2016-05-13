package com.iservport.mindful.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.user.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositório auxiliar para UserRole.
 * 
 * @author mauriciofernandesdecastro
 *
 */
public interface UserRoleTmpRepository 
	extends JpaRepository<UserRole, Serializable>
{
	
	/**
	 * Pesquisa por grupo e serviço.
	 * 
	 * @param userGroupId
	 * @param serviceName
	 * @param serviceExtensions
	 */
	List<UserRole> findByUserGroupIdAndServiceServiceNameAndServiceServiceExtensionsContains(
			int userGroupId, String serviceName, String serviceExtensions);
	
	/**
	 * Pesquisa por usuário.
	 * 
	 * @param userId
	 * @param page
	 */
	@Query(
			"select new "
			+ "com.iservport.mindful.repository.UserRoleReadAdapter"
			+ "( userRole.id" 
			+ ", userAssociation.child.id" 
			+ ", userRole.service.id" 
			+ ", userRole.service.serviceName" 
			+ ", userRole.serviceExtension" 
			+ ", userRole.activityState" 
			+ ", userRole.partnershipExtension" 
			+ ") "
			+ "from UserAssociation userAssociation "
			+ "inner join userAssociation.parent.roles userRole "
			+ "where userAssociation.child.id = ?1 "
			)
	Page<UserRoleReadAdapter> findByUserId(int userId, Pageable page);
	
	/**
	 * Pesquisa por usuário.
	 * 
	 * @param userId
	 * @param page
	 */
	@Query(
			"select new "
			+ "com.iservport.mindful.repository.UserRoleReadAdapter"
			+ "( userRole.id" 
			+ ", userRole.userGroup.id" 
			+ ", userRole.service.id" 
			+ ", userRole.service.serviceName" 
			+ ", userRole.serviceExtension" 
			+ ", userRole.activityState" 
			+ ", userRole.partnershipExtension" 
			+ ") "
			+ "from UserRole userRole "
			+ "where userRole.id = ?1 "
			)
	UserRoleReadAdapter findByUserRoleId(int userRoleId);

	@Query(
			"SELECT userRole.id "
			+ "FROM UserRole userRole "
			+ "WHERE userRole.userGroup.id = ?1 "
			+ "AND userRole.service.id = ?2 "
			+ "AND LOWER(userRole.serviceExtension) LIKE ?3 "
			)
	Integer findByUserIdAndServiceIdAndServiceExtensionLike(Integer userId, Integer serviceId, String ServiceExtension);

}

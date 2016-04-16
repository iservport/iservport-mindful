package com.iservport.mindful.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.user.domain.User;
import org.helianto.user.domain.UserAssociation;
import org.helianto.user.domain.UserGroup;
import org.helianto.user.repository.UserReadAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositório de estatísticas para usuários.
 * 
 * @author mauriciofernandesdecastro
 */
public interface UserTmpRepository 
	extends JpaRepository<User, Serializable> 
{

	@Query("select parents.parent "
			+ "from User child "
			+ "join child.parentAssociations parents "
			+ "where parents.parent.userKey = 'USER' "
			+ "and child.id = ?1 ")
	UserGroup findAllUserGroup(int userId);
	
	/**
	 * Find by parent id and state, pageable.
	 * 
	 * @param parentGroupId
	 * @param userState
	 * @param page
	 */
	@Query(value="select new "
			+ "org.helianto.user.repository.UserReadAdapter"
			+ "(child.id, parents.parent.id, child.userKey, child.userName"
			+ ", child.userState, child.identity.personalData.gender) "
			+ "from User child "
			+ "join child.parentAssociations parents "
			+ "where parents.parent.id = ?1 "
			+ "and child.userState in ?2 ")
	Page<UserReadAdapter> findByParent(int parentGroupId, char[] userState, Pageable page);
	
	/**
	 * Find by parent key and state, pageable.
	 * 
	 * @param entityId
	 * @param userKey
	 * @param userState
	 * @param page
	 */
	@Query(value="select new "
			+ "org.helianto.user.repository.UserReadAdapter"
			+ "(child.id, parents.parent.id, child.userKey, child.userName"
			+ ", child.userState, child.identity.personalData.gender) "
			+ "from User child "
			+ "join child.parentAssociations parents "
			+ "where parents.parent.entity.id = ?1 "
			+ "and parents.parent.userKey = ?2 "
			+ "and child.userState in ?3 ")
	Page<UserReadAdapter> findByParentUserKey(int entityId, String userKey, char[] userState, Pageable page);
	
	/**
	 * Find by parent key and state, pageable.
	 * 
	 * @param entityId
	 * @param userKey
	 * @param exclusions
	 * @param userState
	 * @param page
	 */
	@Query(value="select new "
			+ "org.helianto.user.repository.UserReadAdapter"
			+ "(child.id, parents.parent.id, child.userKey, child.userName"
			+ ", child.userState, child.identity.personalData.gender) "
			+ "from User child "
			+ "join child.parentAssociations parents "
			+ "where parents.parent.entity.id = ?1 "
			+ "and parents.parent.userKey = ?2 "
			+ "and child.id not in ?3 "
			+ "and child.userState in ?4 ")
	Page<UserReadAdapter> findByParentUserKey(int entityId, String userKey, Integer[] exclusions, char[] userState, Pageable page);
	
	@Query(value="select new "
			+ "org.helianto.user.repository.UserReadAdapter"
			+ "(child.id, parents.parent.id, child.userKey, child.userName"
			+ ", child.userState, child.identity.personalData.gender) "
			+ "from User child "
			+ "join child.parentAssociations parents "
			+ "where lower(parents.parent.userKey) = 'user' "
			+ "and child.id in ("
			+ "  select u.id from User u "
			+ "  where u.entity.id = ?1 "
			+ "  and ( lower(u.userKey) like ?2 or lower(u.userName) like ?2 ) "
			+ ") ")
	public Page<UserReadAdapter> findByEntity_IdAndSearchString(int entityId, String searchString, Pageable page);
	
//	/**
//	 * Lista usuários de uma entidade e tipo.
//	 * 
//	 * @param entityId
//	 */
//	@Query("select new "
//			+ "com.iservport.user.repository.adapter.UserGroupReadAdapter"
//			+ "(userGroup.id, userGroup.userKey, userGroup.userName, "
//			+ "userGroup.minimalEducationRequirement, userGroup.minimalExperienceRequirement, "
//			+ "userGroup.userType) "
//			+ "from UserGroup userGroup "
//			+ "where userGroup.entity.id = ?1 "
//			+ "and userGroup.userType = ?2 "
//			+ "and userGroup.userState = 'A' ")
//	Page<UserGroupReadAdapter> findActiveUserGroupsByUserType(int entityId, char userType, Pageable page);
//
//	/**
//	 * Lista grupos de usuários associados a um usuário.
//	 * 
//	 * @param child
//	 */
//	@Query("select new "
//			+ "com.iservport.user.repository.adapter.UserGroupReadAdapter"
//			+ "(association.parent.id, association.parent.userKey, association.parent.userName, "
//			+ "association.parent.minimalEducationRequirement, "
//			+ "association.parent.minimalExperienceRequirement, "
//			+ "association.parent.userType) "
//			+ "from UserAssociation association "
//			+ "where association.child.id = ?1 ")
//	List<UserGroupReadAdapter> findParentsByChildId(int childId, Pageable page);
	
	/**
	 * Lista id de grupos de usuários associados a um usuário.
	 * 
	 * @param child
	 */
	@Query("select association.parent.id "
			+ "from UserAssociation association "
			+ "where association.child.id = ?1 ")
	List<Integer> findParentIdsByChildId(int userId);

	/**
	 * Lista id de grupos de usuários associados a um usuário.
	 * 
	 * @param child
	 */
	@Query("select association.parent.id "
			+ "from UserAssociation association "
			+ "where association.child.id = ?1 "
			+ "and association.parent.userType not in ('A', 'G') "
			+ "and association.parent.userKey != 'USER' "
			+ "and association.parent.userKey != 'ADMIN' ")
	List<Integer> findUnprotectedParentIdsByChildId(int userId);

	/**
	 * Pesquisa a associação da identidade através do usuário da entidade.
	 * 
	 * @param identityId
	 * @param entityId
	 */
	@Query("select association "
			+ "from UserAssociation association "
			+ "where association.child.identity.id = ?1 "
			+ "and association.parent.entity.id = ?2 "
			+ "and association.parent.userKey = 'USER' ")
	UserAssociation findAssociations(int identityId, int entityId);

//	/**
//	 * Pesquisa grupos da entidade.
//	 * 
//	 * @param entityId
//	 * @param page
//	 */
//	@Query("select new "
//			+ "com.iservport.user.repository.adapter.UserGroupReadAdapter"
//			+ "(userGroup.id, userGroup.userKey, userGroup.userName, userGroup.userType) "
//			+ "from UserGroup userGroup "
//			+ "where userGroup.entity.id = ?1 "
//			+ "and userGroup.class = 'G' "
//			+ "and userGroup.userType not in ('A', 'G')")
//	List<UserGroupReadAdapter> findByEntity_Id(Integer entityId, Pageable page);
		
}

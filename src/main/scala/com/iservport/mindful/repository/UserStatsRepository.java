package com.iservport.mindful.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.helianto.core.internal.SimpleCounter;
import org.helianto.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Repositório de estatísticas para usuários.
 * 
 * @author mauriciofernandesdecastro
 */
public interface UserStatsRepository 
	extends JpaRepository<User, Serializable> 
{

	// contagem por qualificador
	//
	
	/**
	 * Conta usuários ativos de uma entidade, agrupados por natureza.
	 * 
	 * @param entityId
	 */
	@Query("select new " +
			"org.helianto.core.internal.SimpleCounter" +
			"(parents.parent.userType, count(user)) " +
			"from User user " +
			"join user.parentAssociations parents " +
			"where user.entity.id = ?1 " +
			"and user.userState = 'A' " +
			"group by parents.parent.userType ")
	List<SimpleCounter> countActiveUsersGroupByType(int entityId);

	/**
	 * Conta usuários ativos com pendências em uma entidade, agrupados por natureza.
	 * 
	 * @param entityId
	 * @param checkDate
	 */
	@Query("select new " +
			"org.helianto.core.internal.SimpleCounter" +
			"(parents.parent.userType, count(user)) " +
			"from User user " +
			"join user.parentAssociations parents " +
			"where user.entity.id = ?1 " +
			"and user.userState = 'A' " +
			"group by parents.parent.userType ")
	List<SimpleCounter> countUsersGroupByType(int entityId, Date checkDate);

}

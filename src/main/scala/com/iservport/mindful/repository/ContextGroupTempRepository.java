package com.iservport.mindful.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.core.domain.ContextGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ContextGroup temp repository .
 * 
 * @author Eldevan Nery Junior
 */
public interface ContextGroupTempRepository 
	extends JpaRepository<ContextGroup, Serializable> {

	/**
	 * Find by natural key.
	 * 
	 * @param contextId
	 * @param contextGroupCode
	 * @return
	 */
	@Query("select contextGroup "
			+ "from ContextGroup contextGroup "
			+ "where contextGroup.context.id = ?1 "
			+ "and contextGroup.contextGroupCode = ?2 ")
	public ContextGroup findByContextIdAndContextGroupCode(int contextId, String contextGroupCode);
	
	/**
	 * Find by Id.
	 * 
	 * @param id
	 */
	public List<ContextGroup> findById(Integer id);

}

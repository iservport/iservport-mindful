package com.iservport.mindful.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iservport.mindful.domain.Voto;

/**
 * Repositório de votos.
 * 
 * @author mauriciofernandesdecastro
 */
public interface VotoRepository 
	extends JpaRepository<Voto, Serializable> {

	@Query("select voto.voto "
			+ "from Voto voto "
			+ "where voto.documento.id = ?1 "
			+ "and voto.eleitor.id = ?2 ")
	Integer findByDocumentoLegislativoIdAndUserId(Integer documentId, Integer userId);
	
}

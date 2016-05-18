package com.iservport.mindful.repository;

import com.iservport.mindful.domain.Vote;
import org.helianto.core.internal.SimpleCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Repositório de votos.
 *
 * @author mauriciofernandesdecastro
 */
public interface VoteRepository
		extends JpaRepository<Vote, Serializable> {

	@Query("select voto.voto "
			+ "from Vote voto "
			+ "where voto.documento.id = ?1 "
			+ "and voto.eleitor.id = ?2 ")
	Integer findByDocumentoLegislativoIdAndUserId(Integer documentId, Integer userId);

	@Query("select new "
			+"org.helianto.core.internal.SimpleCounter"
			+ "(voto.voto, count(voto)) "
			+ "from Vote voto "
			+ "where voto.documento.id = ?1 "
			+ "group by voto.voto ")
	List<SimpleCounter> countVotosByDocumentoLegislativoId(Integer id);

	@Query("select new "
			+"org.helianto.core.internal.SimpleCounter"
			+ "(voto.voto, count(voto)) "
			+ "from Vote voto "
			+ "where voto.documento.id = ?1 "
			+ "and voto.voto = ?2 "
			+ "group by voto.voto ")
	SimpleCounter countByDocumentoLegislativoId(Integer id, Integer voteType);

}

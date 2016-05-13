package com.iservport.mindful.repository;

import java.io.Serializable;
import java.util.List;

import org.helianto.core.internal.SimpleCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iservport.mindful.domain.DocumentoLegislativo;

/**
 * Repositório de documentos legislativos.
 * 
 * @author mauriciofernandesdecastro
 */
public interface DocumentoLegislativoRepository 
	extends JpaRepository<DocumentoLegislativo, Serializable> {

	
	@Query("select new " 
			+"org.helianto.core.internal.SimpleCounter"
			+ "(voto.voto, count(voto)) "
			+ "from Voto voto "
			+ "where voto.documento.id = ?1 "
			+ "group by voto.voto ")
	List<SimpleCounter> countVotosByDocumentoLegislativoId(Integer id);
	
	@Query("select count(voto) "
			+ "from Voto voto "
			+ "where voto.documento.id = ?1 "
			+ "and voto.voto = 1 ")
	Integer countAgreeByDocumentoLegislativoId(Integer id);
	
	@Query("select count(voto) "
			+ "from Voto voto "
			+ "where voto.documento.id = ?1 "
			+ "and voto.voto = 0 ")
	Integer countDisagreeByDocumentoLegislativoId(Integer id);
	
	/**
	 * Find by resolution
	 * 
	 * @param resolution
	 */
	@Query("select count(documentoLegislativo) "
			+ "from DocumentoLegislativo documentoLegislativo "
			+ "where documentoLegislativo.resolution = ?1 "
			+ "and documentoLegislativo.entity.id = ?2 ")
	Integer countDocumentoLegislativoByResolution(Character resolution, Integer entityId);
	
	/**
	 * Find feature
	 * 
	 * @param resolution
	 */
	@Query("select new " 
			+ "org.helianto.core.internal.SimpleCounter"
			+ "(feature.featureCode, count(doc)) "
			+ "from Feature feature, DocumentoLegislativo doc "
			+ "where feature.context.id = ?1 and doc.feature.id = feature.id "
			+ "group by doc.feature.id ")
	List<SimpleCounter> countFeatureByContextId(int contextId);
	
	@Query("select new "
			+ "com.iservport.mindful.repository.DocumentoLegislativoReadAdapter"
			+ "(doc.id"			
			+ ", doc.docCode"
			+ ", doc.docName"
			+ ", doc.nextCheckDate"
			+ ", doc.docAbstract"
			+ ", doc.resolution"
			+ ", doc.feature.id"
			+ ", doc.content"
			+ ", doc.author.id"
			+ ", doc.feature.featureName"
			+ ", doc.author.identity.displayName) "
			+ "from DocumentoLegislativo doc "
			+ "where doc.entity.id = ?1")
	List<DocumentoLegislativoReadAdapter> findByEntityId(Integer entityId);

	
	@Query("select new "
			+ "com.iservport.mindful.repository.DocumentoLegislativoReadAdapter"
			+ "(doc.id"			
			+ ", doc.docCode"
			+ ", doc.docName"
			+ ", doc.nextCheckDate"
			+ ", doc.docAbstract"
			+ ", doc.resolution"
			+ ", doc.feature.id"
			+ ", doc.content"
			+ ", doc.author.id"
			+ ", doc.feature.featureName"
			+ ", doc.author.identity.displayName) "
			+ "from DocumentoLegislativo doc "
			+ "where doc.id = ?1")
	DocumentoLegislativoReadAdapter findById(Integer id);
	
}

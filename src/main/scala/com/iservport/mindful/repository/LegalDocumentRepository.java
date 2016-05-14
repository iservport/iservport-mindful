package com.iservport.mindful.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iservport.mindful.domain.LegalDocument;

/**
 * Repositório de documentos legislativos.
 * 
 * @author mauriciofernandesdecastro
 */
public interface LegalDocumentRepository
	extends JpaRepository<LegalDocument, Serializable> {

	
	/**
	 * Find by resolution
	 * 
	 * @param resolution
	 */
	@Query("select count(documentoLegislativo) "
			+ "from LegalDocument documentoLegislativo "
			+ "where documentoLegislativo.resolution = ?1 "
			+ "and documentoLegislativo.entity.id = ?2 ")
	Integer countDocumentoLegislativoByResolution(Character resolution, Integer entityId);
	
	@Query("select new "
			+ "com.iservport.mindful.repository.LegalDocumentReadAdapter"
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
			+ "from LegalDocument doc "
			+ "where doc.entity.id = ?1")
	List<LegalDocumentReadAdapter> findByEntityId(Integer entityId);

	
	@Query("select new "
			+ "com.iservport.mindful.repository.LegalDocumentReadAdapter"
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
			+ "from LegalDocument doc "
			+ "where doc.id = ?1")
	LegalDocumentReadAdapter findById(Integer id);
	
}

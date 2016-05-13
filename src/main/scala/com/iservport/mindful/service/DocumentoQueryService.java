
package com.iservport.mindful.service;

import com.iservport.mindful.repository.DocumentoLegislativoReadAdapter;
import com.iservport.mindful.repository.DocumentoLegislativoRepository;
import com.iservport.mindful.repository.VotoDetails;
import org.helianto.core.internal.QualifierAdapter;
import org.helianto.core.internal.SimpleCounter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para leitura de documentos.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class DocumentoQueryService {
	
	@Inject
	private DocumentoLegislativoRepository documentoLegislativoRepository;

	/**
	 * Lista prioridades como qualificadores.
	 * 
	 * @param entityId
	 */
	public List<QualifierAdapter> qualifier(int entityId) {
		List<QualifierAdapter> qualifierList = new ArrayList<>();
		
		// count
		// TODO contar por prioridade
		
		return qualifierList;
	}
	
	public Integer getTotalVoto(Integer documentId, Integer type){
		if(type.equals(0)){
			return documentoLegislativoRepository.countDisagreeByDocumentoLegislativoId(documentId);
		}else{
			return documentoLegislativoRepository.countAgreeByDocumentoLegislativoId(documentId);	
		}
	} 
	
	
	public VotoDetails getVotoDetails(Integer documentId){
		Integer disagree = documentoLegislativoRepository.countDisagreeByDocumentoLegislativoId(documentId);
		Integer agree = documentoLegislativoRepository.countAgreeByDocumentoLegislativoId(documentId);	
		return new VotoDetails(agree, disagree, disagree+agree);
	} 
	
	public List<SimpleCounter> getFeatureCounters(Integer contextId){
		return documentoLegislativoRepository.countFeatureByContextId(contextId);
	}
	
	public Integer getTotalDocumentos(Character resolution, Integer entityId){
		return documentoLegislativoRepository.countDocumentoLegislativoByResolution(resolution, entityId);
	}
	
	public List<SimpleCounter> getVotos(Integer documentoId){
		return documentoLegislativoRepository.countVotosByDocumentoLegislativoId(documentoId);
	}
	
	/**
	 * Find list by entityId
	 * 
	 * @param entityId
	 */
	public List<DocumentoLegislativoReadAdapter> getDocumentos(Integer entityId){
		return documentoLegislativoRepository.findByEntityId(entityId); 
	}
	
	/**
	 * Find One.
	 *   
	 * @param id
	 * 
	 */
	public DocumentoLegislativoReadAdapter getDocumento(Integer id){
		return documentoLegislativoRepository.findById(id);
	}
	
}


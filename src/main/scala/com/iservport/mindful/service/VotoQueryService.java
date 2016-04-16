package com.iservport.mindful.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.iservport.mindful.repository.VotoRepository;

/**
 * Serviço para leitura de votos.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class VotoQueryService {

	@Inject
	protected VotoRepository votoRepository;
	
	/**
	 * Retorna o voto de um usuário num projeto.
	 * 
	 * @param documentoId
	 * @param userId
	 */
	public Integer getDocumentoVoto(Integer documentoId, Integer userId){
		return votoRepository.findByDocumentoLegislativoIdAndUserId(documentoId, userId);
	}	
	
}

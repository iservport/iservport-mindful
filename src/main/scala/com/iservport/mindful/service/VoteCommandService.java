package com.iservport.mindful.service;

import javax.inject.Inject;

import org.helianto.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.iservport.mindful.domain.Voto;
import com.iservport.mindful.repository.DocumentoLegislativoReadAdapter;
import com.iservport.mindful.repository.DocumentoLegislativoRepository;
import com.iservport.mindful.repository.VotoRepository;

/**
 * Serviço para escrita de votos.
 * 
 * @author mauriciofernandesdecastro
 */
@Service
public class VoteCommandService {
	@Inject 
	private DocumentoLegislativoRepository documentoLegislativoRepository;
	
	@Inject
	private UserRepository userRepository; 
	
	@Inject 
	private VotoRepository votoRepository;
	
	public DocumentoLegislativoReadAdapter voto(Integer documentoId, Integer userId, Integer voto) {
		Voto voted = new Voto(documentoLegislativoRepository.findOne(documentoId), userRepository.findOne(userId));
		voted.setVoto(voto);
		votoRepository.saveAndFlush(voted);
		return documentoLegislativoRepository.findById(documentoId);
	}
	
}

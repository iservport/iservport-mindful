package com.iservport.mindful.controller;

import com.iservport.mindful.repository.DocumentoLegislativoReadAdapter;
import com.iservport.mindful.service.VoteCommandService;
import com.iservport.mindful.service.VoteQueryService;
import org.helianto.security.internal.UserAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Controlador de votos.
 * 
 * @author mauriciofernandesdecastro
 */
@RequestMapping(value={"/app/voto"})
@Controller
public class VoteSearchController {

	private static final Logger logger = LoggerFactory.getLogger(VoteSearchController.class);
	
	@Inject
	private VoteQueryService voteQueryService;
	
	@Inject
	private VoteCommandService voteCommandService;
	
	
	/**
	 * Home.
	 * 
	 * GET		/app/voto/
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value={"/", ""}, method=RequestMethod.GET)
	public String home(UserAuthentication userAuthentication, Model model) {
		String base = "voto";
		logger.info("User id: {} loading {} page.", userAuthentication.getUserId(), base);
		model.addAttribute("baseName", base);
		model.addAttribute("layoutSize", "2");
		return "frame-angular";
	}
	
	/**
	 * Voto.
	 * 
	 * GET		/app/voto/?documentoId&voto
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value={"/", ""}, method=RequestMethod.GET, params={"documentoId", "voto"})
	@ResponseBody
	public DocumentoLegislativoReadAdapter voto(UserAuthentication userAuthentication, @RequestParam Integer documentoId, @RequestParam Integer voto) {
		DocumentoLegislativoReadAdapter documento = voteCommandService.voto(documentoId, userAuthentication.getUserId(), voto);
		Integer canVoteInteger = voteQueryService.getDocumentoVoto(documentoId, userAuthentication.getUserId());
		Boolean canVote = true;
		if(canVoteInteger!=null && canVoteInteger>=0){
			canVote = false;
		}
		documento.setVote(canVote);
		return documento;
	}
	
	
}

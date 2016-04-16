package com.iservport.mindful.controller

import java.util.List
import javax.inject.Inject
import org.helianto.core.internal.SimpleCounter
import org.helianto.core.repository.EntityReadAdapter
import org.helianto.security.internal.UserAuthentication
import org.helianto.user.repository.UserReadAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import com.iservport.mindful.internal.QualifierAdapter
import com.iservport.mindful.repository.DocumentoLegislativoReadAdapter
import com.iservport.mindful.repository.VotoDetails
import com.iservport.mindful.service.DocumentoCommandService
import com.iservport.mindful.service.DocumentoQueryService
import com.iservport.mindful.service.HomeCommandService
import com.iservport.mindful.service.HomeQueryService
import com.iservport.mindful.service.VotoQueryService

@RequestMapping(value = Array("/app/home", "/home", "/"))
@Controller class HomeSearchController {

  private val logger: Logger = LoggerFactory.getLogger(classOf[HomeSearchController])
  
  @Inject private val homeQueryService: HomeQueryService = null
  @Inject private val homeCommandService: HomeCommandService = null
  @Inject private val documentoQueryService: DocumentoQueryService = null
  @Inject private val documentoCommandService: DocumentoCommandService = null
  @Inject private val votoQueryService: VotoQueryService = null

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/", ""), method = Array(RequestMethod.GET)) def home(userAuthentication: UserAuthentication, model: Model): String = {
    logger.info("User id: {} loading home page.", userAuthentication.getUserId)
    model.addAttribute("title", "Cidadão no plenário")
    model.addAttribute("baseName", "home")
    model.addAttribute("layoutSize", "3")
    return "frame-angular"
  }

  @RequestMapping(value = Array("/error"), method = Array(RequestMethod.GET)) def loginError(model: Model, @RequestParam `type`: String): String = {
    model.addAttribute("loginFailMsg", "label.user.error." + `type`)
    model.addAttribute("error", true)
    return "security/login"
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/entity"), method = Array(RequestMethod.GET))
  @ResponseBody def entity(userAuthentication: UserAuthentication): EntityReadAdapter = {
    return homeQueryService.entity(userAuthentication.getEntityId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/user"), method = Array(RequestMethod.GET))
  @ResponseBody def user(userAuthentication: UserAuthentication): UserReadAdapter = {
    return homeQueryService.user(userAuthentication.getUserId)
  }

  @RequestMapping(value = Array("/login"), method = Array(RequestMethod.GET)) def signin(error: String, model: Model, @RequestParam(required = false) logout: String): String = {
    if (error != null && error == "1") {
      model.addAttribute("error", "1")
    }
    return "security/login"
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/stats"), method = Array(RequestMethod.GET))
  @ResponseBody def stats(userAuthentication: UserAuthentication): java.util.List[AnyRef] = {
    return null
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/qualifier"), method = Array(RequestMethod.GET))
  @ResponseBody def qualifier(userAuthentication: UserAuthentication): java.util.List[QualifierAdapter] = {
    return documentoQueryService.qualifier(userAuthentication.getEntityId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("type", "documentoId"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def votos(userAuthentication: UserAuthentication, @RequestParam `type`: Integer, @RequestParam documentoId: Integer): Integer = {
    return documentoQueryService.getTotalVoto(documentoId, `type`)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("documentoId", "details"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def getVotoDetails(userAuthentication: UserAuthentication, @RequestParam documentoId: Integer): VotoDetails = {
    return documentoQueryService.getVotoDetails(documentoId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/feature"), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def features(userAuthentication: UserAuthentication): java.util.List[SimpleCounter] = {
    return documentoQueryService.getFeatureCounters(userAuthentication.getContextId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("resolution"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def totalDocumentos(userAuthentication: UserAuthentication, @RequestParam resolution: Character): Integer = {
    return documentoQueryService.getTotalDocumentos(resolution, userAuthentication.getEntityId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("documentoId"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def features(@RequestParam documentoId: Integer): java.util.List[SimpleCounter] = {
    return documentoQueryService.getVotos(documentoId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("all"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def documentos(userAuthentication: UserAuthentication): java.util.List[DocumentoLegislativoReadAdapter] = {
    return documentoQueryService.getDocumentos(userAuthentication.getEntityId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("documentoId", "one"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def documentos(userAuthentication: UserAuthentication, @RequestParam documentoId: Integer): DocumentoLegislativoReadAdapter = {
    val documento: DocumentoLegislativoReadAdapter = documentoQueryService.getDocumento(documentoId)
    val canVoteInteger: Integer = votoQueryService.getDocumentoVoto(documentoId, userAuthentication.getUserId)
    var canVote: Boolean = true
    if (canVoteInteger != null && canVoteInteger >= 0) {
      canVote = false
    }
    documento.setVote(canVote)
    return documento
  }
}
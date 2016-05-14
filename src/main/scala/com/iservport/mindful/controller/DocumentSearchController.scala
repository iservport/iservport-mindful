package com.iservport.mindful.controller

import javax.inject.Inject

import com.iservport.mindful.domain.LegalDocument
import com.iservport.mindful.service.{DocumentCommandService, DocumentQueryService}
import org.helianto.security.internal.UserAuthentication
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, RestController}

/**
  * Document search controller.
  */
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = Array("/app/document"))
@RestController
class DocumentSearchController {

  @Inject private val queryService: DocumentQueryService = null
  @Inject private val commandService: DocumentCommandService = null

  /**
    * Gets a list of documents from an Entity.
    *
    * GET /app/document/?all
    *
    * @param userAuthentication
    */
  // TODO-document-controller instead of a list, get a page of docs?
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("all"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def getDocuments(userAuthentication: UserAuthentication) =
    queryService.getDocuments(userAuthentication.getEntityId)

  /**
    * Gets document count by resolution.
    *
    * GET /app/document/?resolution
    *
    * @param userAuthentication
    * @param resolution
    */
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("resolution"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def getDocumentCountByResolution(userAuthentication: UserAuthentication, @RequestParam resolution: Char) =
    queryService.getTotalDocuments(resolution, userAuthentication.getEntityId)

  /**
    * Saves or updates a document.
    *
    * PUT /app/document/
    *
    * @param userAuthentication
    * @param command
    */
  @RequestMapping(method = Array(RequestMethod.PUT))
  def putDocumentToSaveOrUpdate(userAuthentication: UserAuthentication, @RequestParam command: LegalDocument) =
    commandService.saveOrUpdate(userAuthentication.getEntityId, userAuthentication.getIdentityId, command)

}

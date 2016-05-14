package com.iservport.mindful.controller

import com.iservport.mindful.repository.{LegalDocumentReadAdapter, VoteDetails}
import com.iservport.mindful.service.VoteCommandService
import com.iservport.mindful.service.VoteQueryService
import org.helianto.security.internal.UserAuthentication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.inject.Inject

import org.helianto.core.internal.SimpleCounter
import org.springframework.http.MediaType

/**
  * Vote search controller.
  */
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = Array("/app/vote"))
@Controller
class VoteSearchController {

  @Inject private val queryService: VoteQueryService = null
  @Inject private val commandService: VoteCommandService = null

  /**
    * Count votes by type.
    *
    * GET /app/vote/?type&documentId
    *
    * @param documentoId
    * @param `type`
    */
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("type", "documentId"))
  def getVoteSimpleCountByType(documentoId: Int, `type`: Int) =
    queryService.getVoteSimpleCountByType(documentoId, `type`)

  /**
    * Count votes by document.
    *
    * GET /app/vote/?documentId
    *
    * @param documentoId
    */
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("documentoId"))
  def features(@RequestParam documentoId: Integer): java.util.List[SimpleCounter] = {
    queryService.getVoteSimpleCountByType(documentoId)
  }

  /**
    * Gets voting details.
    *
    * GET /app/vote/?documentId&details
    *
    * @param userAuthentication
    * @param documentoId
    */
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("documentoId", "details"))
  def getVoteDetails(userAuthentication: UserAuthentication, @RequestParam documentoId: Integer) =
    queryService.getVoteDetails(documentoId)

  /**
    * Gets the  a vote.
    *
    * PUT /app/vote/?type&documentId
    *
    * @param userAuthentication
    * @param documentoId
    */
  @RequestMapping(method = Array(RequestMethod.PUT), params = Array("documentoId"))
  def putVoteToCast(userAuthentication: UserAuthentication, @RequestParam documentoId: Int) =
    queryService.getVote(userAuthentication.getUserId, documentoId)

}
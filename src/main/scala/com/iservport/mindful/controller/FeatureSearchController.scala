package com.iservport.mindful.controller

import javax.inject.Inject

import com.iservport.mindful.service.{FeatureQueryService, HomeQueryService}
import org.helianto.core.internal.SimpleCounter
import org.helianto.security.internal.UserAuthentication
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod}

/**
  * Feature assignment controller.
  *
  * <p>Politikei features are the user possible interests.</p>
  */
@RequestMapping(value = Array("/app/feature"))
@Controller
class FeatureSearchController {

  @Inject private val queryService: FeatureQueryService = null

  /**
    * Gets feature count.
    *
    * @param userAuthentication
    */
  @RequestMapping(method = Array(RequestMethod.GET))
  def getFeatureCount(userAuthentication: UserAuthentication) =
    queryService.getFeatureCounters(userAuthentication.getContextId)

}

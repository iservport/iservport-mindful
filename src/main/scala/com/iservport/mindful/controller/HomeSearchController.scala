package com.iservport.mindful.controller


import javax.inject.Inject

import com.iservport.mindful.service._
import org.helianto.security.internal.UserAuthentication
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation._

/**
  * Politikei home controller.
  */
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = Array("/app/home"))
@RestController
class HomeSearchController {

  @Inject private val homeQueryService: HomeQueryService = null

  /**
    * Loads the logged entity.
    *
    * @param userAuthentication
    */
  @RequestMapping(value = Array("/entity"), method = Array(RequestMethod.GET))
  def entity(userAuthentication: UserAuthentication) =
    homeQueryService.entity(userAuthentication.getEntityId)

  /**
    * Loads the logged user.
    *
    * @param userAuthentication
    */
  @RequestMapping(value = Array("/user"), method = Array(RequestMethod.GET))
  def user(userAuthentication: UserAuthentication) =
    homeQueryService.user(userAuthentication.getUserId)

  /**
    * Some stats.
    *
    * @param userAuthentication
    */
  @RequestMapping(value = Array("/stats"), method = Array(RequestMethod.GET))
  def stats(userAuthentication: UserAuthentication): java.util.List[AnyRef] = {
    // TODO-home-controller stats
    null
  }

  /**
    * Loads qualifiers.
    *
    * <p>Politikei qualifies entities according to level: city, state, lower house, upper house.</p>
    *
    * @param userAuthentication
    */
  @RequestMapping(value = Array("/qualifier"), method = Array(RequestMethod.GET))
  def qualifier(userAuthentication: UserAuthentication) =
    homeQueryService.qualifier(userAuthentication.getEntityId)

}
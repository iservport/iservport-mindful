//package com.iservport.mindful.controller
//
//import javax.inject.Inject
//import javax.servlet.http.Cookie
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//import org.helianto.security.internal.UserAuthentication
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices
//import org.springframework.util.StringUtils
//import org.springframework.web.bind.annotation._
//import com.iservport.mindful.service.{HouseCommandService, HouseQueryService}
//
///**
//  * House search controller.
//  */
//@PreAuthorize("isAuthenticated()")
//@RequestMapping(value = Array("/app/house/entity"))
//@RestController
//class HouseSearchController {
//
//  private val logger: Logger = LoggerFactory.getLogger(classOf[HouseSearchController])
//
//  @Inject val queryService: HouseQueryService = null
//  @Inject val commandService: HouseCommandService = null
//
//  /**
//    * List qualifiers, i.e., type of legislative houses.
//    *
//    * @param userAuthentication
//    */
//  @RequestMapping(value = Array("/qualifier"), method = Array(RequestMethod.GET))
//  def qualifier(userAuthentication: UserAuthentication) =
//    queryService.qualifier(userAuthentication.getEntityId)
//
//  @RequestMapping(method = Array(RequestMethod.GET), params = Array("entityType"))
//  def entities(userAuthentication: UserAuthentication, @RequestParam entityType: Character) =
//    queryService.listEntities(userAuthentication.getIdentityId, entityType)
//
//  @RequestMapping(method = Array(RequestMethod.GET), params = Array("userId"))
//  def entity(userAuthentication: UserAuthentication, @RequestParam userId: Integer) =
//    queryService.entity(userId)
//
//  @RequestMapping(method = Array(RequestMethod.GET), params = Array("rootUserId"))
//  def authorize(userAuthentication: UserAuthentication, request: HttpServletRequest, response: HttpServletResponse, @RequestParam rootUserId: Integer): String = {
//    if (commandService.authorize(rootUserId, userAuthentication.getUserId)) {
//      cancelRemeberMeCookie(request, response)
//      return "{\"success\":1}"
//    }
//    return null
//  }
//
//  protected def cancelRemeberMeCookie(request: HttpServletRequest, response: HttpServletResponse) {
//    val cookieName: String = AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY
//    val cookie: Cookie = new Cookie(cookieName, null)
//    cookie.setMaxAge(0)
//    cookie.setPath(if (StringUtils.hasLength(request.getContextPath)) request.getContextPath
//    else "/")
//    response.addCookie(cookie)
//  }
//
//}
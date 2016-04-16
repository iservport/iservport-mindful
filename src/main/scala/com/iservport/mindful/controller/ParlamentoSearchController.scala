package com.iservport.mindful.controller

import java.io.Serializable
import java.util.List
import javax.inject.Inject
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.helianto.core.repository.EntityReadAdapter
import org.helianto.security.internal.UserAuthentication
import org.helianto.user.repository.UserEntityAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import com.iservport.mindful.internal.QualifierAdapter
import com.iservport.mindful.service.ParlamentoCommandService
import com.iservport.mindful.service.ParlamentoQueryService

@RequestMapping(value = Array("/app/parlamento"))
@Controller class ParlamentoSearchController {

  private val logger: Logger = LoggerFactory.getLogger(classOf[ParlamentoSearchController])

  @Inject private val parlamentoQueryService: ParlamentoQueryService = null
  @Inject private val parlamentoCommandService: ParlamentoCommandService = null

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET)) def home(userAuthentication: UserAuthentication, model: Model): String = {
    val base: String = "parlamento"
    logger.info("User id: {} loading {} page.", userAuthentication.getUserId, base)
    model.addAttribute("baseName", base)
    model.addAttribute("layoutSize", "2")
    return "frame-angular"
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/qualifier"), method = Array(RequestMethod.GET))
  @ResponseBody def qualifier(userAuthentication: UserAuthentication): java.util.List[QualifierAdapter] = {
    return parlamentoQueryService.qualifier(userAuthentication.getEntityId)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/entity"), method = Array(RequestMethod.GET), params = Array("entityType"))
  @ResponseBody def entities(userAuthentication: UserAuthentication, @RequestParam entityType: Character): Page[EntityReadAdapter] = {
    return parlamentoQueryService.listEntities(userAuthentication.getIdentityId, entityType)
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/entity"), method = Array(RequestMethod.GET), params = Array("userId"))
  @ResponseBody def entity(userAuthentication: UserAuthentication, @RequestParam userId: Integer): UserEntityAdapter = {
    return parlamentoQueryService.entity(userId)
  }

//  @PreAuthorize("isAuthenticated()")
//  @RequestMapping(value = Array("/search"), method = Array(RequestMethod.POST))
//  @ResponseBody def search(userAuthentication: UserAuthentication, @RequestParam(defaultValue = "0") pageNumber: Integer, @RequestBody form: Nothing): Page[UserEntityAdapter] = {
//    return parlamentoQueryService.search(userAuthentication.getEntityId, form.getSearchString, pageNumber)
//  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/entity"), method = Array(RequestMethod.GET), params = Array("rootUserId"))
  @ResponseBody def authorize(userAuthentication: UserAuthentication, request: HttpServletRequest, response: HttpServletResponse, @RequestParam rootUserId: Integer): String = {
    if (parlamentoCommandService.authorize(rootUserId, userAuthentication.getUserId)) {
      cancelRemeberMeCookie(request, response)
      return "{\"success\":1}"
    }
    return null
  }

  protected def cancelRemeberMeCookie(request: HttpServletRequest, response: HttpServletResponse) {
    val cookieName: String = AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY
    val cookie: Cookie = new Cookie(cookieName, null)
    cookie.setMaxAge(0)
    cookie.setPath(if (StringUtils.hasLength(request.getContextPath)) request.getContextPath
    else "/")
    response.addCookie(cookie)
  }
}
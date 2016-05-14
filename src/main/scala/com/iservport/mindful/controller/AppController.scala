package com.iservport.mindful.controller

import java.util.Locale
import javax.inject.Inject

import org.helianto.core.repository.CategoryRepository
import org.helianto.security.internal.UserAuthentication
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam}

/**
  * Application menu pages controller.
  *
  * @author mauriciofernandesdecastro
  */
@Controller
@RequestMapping(Array("/"))
class AppController extends AbstractAppController {

  /**
    * Menu home.
    *
    * @param userAuthentication
    * @param model
    * @param locale
    */
  @PreAuthorize("isAuthenticated()")
  @RequestMapping(method = Array(RequestMethod.GET))
  def empty(userAuthentication: UserAuthentication, model: Model, locale: Locale) =
    response(userAuthentication, model, "home", false, locale, null)

  /**
    * Generic menu.
    *
    * @param userAuthentication
    * @param model
    * @param baseName
    * @param locale
    * @param externalId optional
    */
  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = Array("/{baseName}", "/{baseName}/"), method = Array(RequestMethod.GET))
  def all(userAuthentication: UserAuthentication, model: Model, @PathVariable baseName: String, locale: Locale, @RequestParam(required = false) externalId: Integer) =
    response(userAuthentication, model, baseName, true, locale, externalId)

  /**
    * Logout.
    *
    * GET		/logout
    */
  @RequestMapping(value = Array("/logout"), method = Array(RequestMethod.GET))
  def logout(userAuthentication: UserAuthentication) = {
    SecurityContextHolder.clearContext
    "redirect:/"
  }

}
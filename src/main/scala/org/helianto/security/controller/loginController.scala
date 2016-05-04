package org.helianto.security.controller

import java.util.Locale
import javax.inject.Inject

import org.helianto.security.service.ResponseService
import org.helianto.user.domain.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam}

/**
  * Login controller.
  *
  * @author mauriciofernandesdecastro
  */
@Controller
@RequestMapping(value = Array("/login"))
class loginController @Inject()(responseService:ResponseService) {

  /**
    * Login page.
    */
  @RequestMapping(method = Array(RequestMethod.GET)) 
  def getSignInPage(error: String, model: Model, locale: Locale) = {
    model.addAttribute("titlePage", "Login")
    if (error != null && (error == "1")) {
      model.addAttribute("error", "1")
    }
    responseService.loginResponse(model, locale)
  }

  /**
    * Login errors.
    *
    * @param model  modelo
    * @param type   tipo de erro
    * @param locale locale
    */
  @RequestMapping(value = Array("/error"), method = Array(RequestMethod.GET))
  def showLoginError(model: Model, @RequestParam `type`: String, locale: Locale) = {
    model.addAttribute("error", true)
    val user: User = new User
    user.setAccountNonExpired(false)
    model.addAttribute("user", user)
    responseService.loginResponse(model, locale)
  }

}
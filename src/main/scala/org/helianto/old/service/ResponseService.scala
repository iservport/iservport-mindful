package org.helianto.old.service

import java.util.Locale
import javax.inject.Inject

import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.ui.Model

/**
  * Created by mauriciofernandesdecastro on 06/03/16.
  */
//@Service
class ResponseService //@Inject()
(env:Environment) {

  /**
    * Default response.
    *
    * @param model    modelo
    * @param locale   locale
    */
  def loginResponse(model: Model, locale: Locale) = {
    model.addAttribute("main", "security/login")
    response(model, locale)
  }

  def signUpResponse(model: Model, locale: Locale): String = {
    model.addAttribute("main", "security/signup")
    response(model, locale)
  }

  def confirmationResponse(model: Model, locale: Locale): String = {
    model.addAttribute("main", "security/password-verify")
    response(model, locale)
  }

  def createResponse(model: Model, locale: Locale): String = {
    model.addAttribute("main", "security/password-create")
    response(model, locale)
  }

  def changeResponse(model: Model, locale: Locale): String = {
    model.addAttribute("main", "security/password-change")
    response(model, locale)
  }

  def recoveryResponse(model: Model, locale: Locale): String = {
    model.addAttribute("main", "security/password-recovery")
    response(model, locale)
  }

  private def response(model: Model, locale: Locale) = {
    model.addAttribute("sender", env.getProperty("iservport.sender.mail"))
    model.addAttribute("baseName", "security")
    model.addAttribute("copyright", env.getProperty("helianto.copyright", ""))
    // TODO tratar locale como é recebido do browser
    // no momento forçamos para pt_BR (stdLocale ao invés de locale)
    val stdlocale = new Locale("pt", "BR")
    if (stdlocale != null) {
      model.addAttribute("locale", stdlocale.toString.toLowerCase)
      model.addAttribute("locale_", stdlocale.toString.replace("_", "-").toLowerCase)
    }
    "frame-security"

  }
}

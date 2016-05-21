package com.iservport.mindful.controller


import java.util.Locale

import org.helianto.security.internal.UserAuthentication
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
  * Application menu pages controller.
  *
  * @author mauriciofernandesdecastro
  */

abstract class AbstractAppController  {

  @RequestMapping(value = Array("favicon.ico"))
  def favicon():String  = "forward:/static/images/favicon.ico"

  /**
    * Default response.
    *
    * @param userAuthentication
    * @param model
    * @param baseName
    * @param navCollapsedMin
    * @param externalId
    * @param locale
    */
  protected def response(userAuthentication: UserAuthentication, model: Model, baseName: String, navCollapsedMin: Boolean, locale: Locale, externalId: Integer) = {
    model.addAttribute("baseName", baseName)
    val stdlocale = new Locale("pt", "BR")
    if (stdlocale != null) {
      model.addAttribute("locale", stdlocale.toString.toLowerCase)
      model.addAttribute("locale_", stdlocale.toString.replace("_", "-").toLowerCase)
    }
    model.addAttribute("navCollapsedMin", navCollapsedMin)
    model.addAttribute("layoutSize", "0")
    getTemplateName(baseName, model)
  }

  /**
    * Default template name.
    */
  protected def getTemplateName(baseName: String, model: Model) = {
    "frame-material"
  }


}
package org.helianto.old.controller

import javax.inject.Inject

import org.helianto.core.domain.Signup
import org.helianto.old.service.{ResponseService, SignUpService, SignUpSocialService}
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._
import org.springframework.web.context.request.WebRequest

/**
  * Base classe to SignUpController.
  *
  * @author mauriciofernandesdecastro
  */
//@Controller
//@RequestMapping(value = Array("/signup"))
class SignUpController //@Inject()
(signUpService:SignUpService, socialService:SignUpSocialService, responseService:ResponseService) {

  /**
    * Signup request page.
    *
    * @param model
    * @param contextId
    * @param principal
    * @param request
    */
  @RequestMapping(method = Array(RequestMethod.GET))
  def getSignupPage(model: Model, @RequestParam(defaultValue = "1") contextId: Integer, @RequestParam(required = false) principal: String, request: WebRequest): String = {
    if (signUpService.notifyAdminIfUserIsNotValid(contextId, principal)) {
      return "forward:/"
    }
//    model.addAttribute("form", socialService.socialSignUpAttempt(contextId, request))
    responseService.signUpResponse(model, request.getLocale)
  }

  /**
    * Check if email exists.
    *
    * @param principal
    */
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("principal"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def verify(@RequestParam principal: String) = {
    "{\"exists\":" + !signUpService.searchPrincipal(new Signup(0, principal)) + "}"
  }

  /**
    * Save Lead
    *
    * @param tempEmail
    */
  @RequestMapping(method = Array(RequestMethod.POST), params = Array("tempEmail"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def saveLead(@RequestParam tempEmail: String): String = {
    if (signUpService.saveLead(tempEmail)) {
      return "{\"exists\": true}"
    }
    return "{\"exists\": false}"
  }

  /**
    * Check if email exists.
    *
    */
  @RequestMapping(method = Array(RequestMethod.POST), params = Array("emailChecked"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody def checkMail(@RequestBody form: Signup): String = {
    return "{\"notExists\":" + signUpService.searchPrincipal(form) + "}"
  }
}
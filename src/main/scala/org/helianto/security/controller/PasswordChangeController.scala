package org.helianto.security.controller

import java.util.Locale
import javax.inject.Inject

import org.helianto.security.service.ResponseService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam}

/**
  * Password change controller.
  */
@Controller
@RequestMapping(value = Array("/change"))
class PasswordChangeController @Inject()
(  passwordService: PasswordService
 , responseService: ResponseService){

  /**
    * Password change request page after e-mail submission.
    *
    * @param model
    * @param confirmationToken
    * @param locale
    */
  @RequestMapping(method = Array(RequestMethod.GET))
  def getChangePasswordPage(model: Model, @RequestParam confirmationToken: String, locale: Locale) =
    passwordService.getIdentityOption(confirmationToken) match {
      case Some(identity) =>
        model.addAttribute("confirmationToken", confirmationToken)
        responseService.changeResponse(model, locale)
      case None =>
        model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.1")
        model.addAttribute("recoveryFail", "true")
        responseService.changeResponse(model, locale)
    }

  /**
    * Do change password.
    *
    * @param model
    * @param confirmationToken
    * @param password
    * @param locale
    */
  @RequestMapping(method = Array(RequestMethod.POST))
  def postChangePassword(model: Model, @RequestParam confirmationToken: String, @RequestParam password: String, locale: Locale) =
    passwordService.getIdentityOption(confirmationToken) match {
      case Some(identity) =>
        model.addAttribute("email", identity.getPrincipal)
        passwordService.changePassword(identity.getPrincipal,password)
        responseService.loginResponse(model, locale)
      case None =>
        model.addAttribute("recoverFail", "false")
        model.addAttribute("recoverFailMsg", "label.user.password.recover.fail.message.0")
        responseService.changeResponse(model, locale)
    }

}
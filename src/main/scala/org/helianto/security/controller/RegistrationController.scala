/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.helianto.security.controller

import java.util.Locale
import javax.inject.Inject

import org.helianto.core.social.SignupForm
import org.helianto.security.service._
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._


/**
  * Registration service.
  */
@Controller
@RequestMapping(Array("/register"))
class RegistrationController @Inject()
(responseService: ResponseService
 , userTokenQueryService: UserTokenQueryService
 , commandService:UserTokenCommandService
 , entityInstallService: EntityInstallService
 , notificationService: SecurityNotification
) {

  private val logger: Logger = LoggerFactory.getLogger(classOf[RegistrationController])


  /**
    * Get the registration page.
    *
    * @param model
    * @param confirmationToken
    * @param locale
    */
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("confirmationToken"))
  def getRegistrationPage(model: Model, @RequestParam confirmationToken: String, locale: Locale): String = {
    userTokenQueryService.findPreviousSignupAttempts(confirmationToken, 5) match {
      case Some(identity) =>
        model.addAttribute("form", new SignupForm(identity.getPrincipal, identity.getIdentityFirstName, identity.getIdentityLastName))
        model.addAttribute("email", entityInstallService.removeLead(identity.getPrincipal))
        responseService.registerResponse(model, locale)
      case _ =>
        model.addAttribute("userConfirmed", false)
        "/signup"
    }
  }

  /**
    * Post the registration page.
    *
    * @param model
    * @param registration
    * @param locale
    *
    */
  @RequestMapping(method = Array(RequestMethod.POST))
  def postRegistrationPage(model: Model, @ModelAttribute registration : Registration , locale: Locale): String = {
    val identity = entityInstallService.installIdentity(registration.getEmail, registration.getPassword);
    model.addAttribute("form", new SignupForm(registration.getEmail, "", ""))
    logger.info("registration {}", registration)
    entityInstallService.entityOption(registration.getContextId, registration.getEntityAlias) match {
      case Some(entity) if registration.isAdmin=>
        // entity exists, but user is not admin
        model.addAttribute("entityExists", true)
        responseService.registerResponse(model, locale)
      case None if !registration.isAdmin =>
        //
        model.addAttribute("entityNotFound", true)
        responseService.registerResponse(model, locale)
      case _ =>
        // otherwise
        val userToken = userTokenQueryService.install(registration)
        try {notificationService.sendWelcome(userToken)} catch {
          case e:Exception  => {
            e.printStackTrace();
          }
        }
        responseService.loginResponse(model, locale)
    }
  }

  /**
    * Ajax call to check if identity already exists.
    *
    * @param principal
    */
  @RequestMapping(method = Array(RequestMethod.GET), params = Array("principal"))
  @ResponseBody
  def isIdentityExisting(@RequestParam principal: String) =
    s"""{"canCreate":${userTokenQueryService.identityExists(principal)}}"""

}
package org.helianto.security.service

import java.util.Locale
import javax.inject.Inject

import org.helianto.core.social.SignupForm
import org.helianto.install.service.SecurityNotification
import org.helianto.security.internal.Registration
import org.springframework.stereotype.Service
import org.springframework.ui.Model

/**
  * Registration service.
  */
@Service
class RegistrationService @Inject()
(entityInstallService: EntityInstallService
 , responseService: ResponseService
 , userTokenCommandService: UserTokenCommandService
 , notificationService: SecurityNotification) {

   /**
     * Do register after registration form submission.
     *
     * @param registration
     * @param model
     * @param locale
     */
   def doRegister(registration:Registration, model: Model, locale: Locale) = {
     val identity = entityInstallService.installIdentity(registration.getEmail, registration.getPassword);
     model.addAttribute("form", new SignupForm(registration.getEmail, "", ""))
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
         val userToken = userTokenCommandService.install(registration)
         Option(entityInstallService.install(registration.getContextId, identity, registration))
           .getOrElse(throw new IllegalArgumentException)
         try {notificationService.sendWelcome(userToken)} catch {
           case e:Exception  => {
             e.printStackTrace();
           }
         }
         responseService.loginResponse(model, locale)
     }

   }

 }

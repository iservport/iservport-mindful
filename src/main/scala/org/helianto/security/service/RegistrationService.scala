package org.helianto.security.service

import java.util.Locale
import javax.inject.Inject

import org.helianto.core.`def`.UserState
import org.helianto.core.domain.{Entity, Lead}
import org.helianto.core.install.EntityInstaller
import org.helianto.core.repository.{CityRepository, EntityRepository, LeadRepository}
import org.helianto.core.social.SignupForm
import org.helianto.install.service.{IdentityInstallService, SecurityNotification, UserInstallService}
import org.helianto.notification.service.SubscriptionNotificationService
import org.helianto.security.internal.Registration
import org.helianto.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.ui.Model

/**
  * Registration service.
  */
@Service
class RegistrationService @Inject()
(cityRepository: CityRepository
 , identityInstallService: IdentityInstallService
 , entityRepository: EntityRepository
 , entityInstaller: EntityInstaller
 , userInstallService: UserInstallService
 , responseService: ResponseService
 , userTokenCommandService: UserTokenCommandService
 , notificationService: SecurityNotification
 , subscriptionService: SubscriptionNotificationService){

  /**
    * Do register after form submission.
    *
    * @param registration
    * @param model
    * @param locale
    */
  def doRegister(registration:Registration, model: Model, locale: Locale) = {
    model.addAttribute("form", new SignupForm(registration.getEmail, "", ""))
    Option(entityRepository.findByOperator_IdAndAlias(registration.getContextId, registration.getEntityAlias)) match {
      case Some(entity) if registration.isAdmin =>
        // user is requiring to become admin of an existing entity: deny
        model.addAttribute("entityExists", true)
        responseService.registerResponse(model, locale)
      case Some(entity) =>
        // user is requiring association to an existing entity: submit to the current admin
        registerPendingUser(registration, entity)
      case None if !registration.isAdmin =>
        // user is requiring association to a new  entity: deny
        model.addAttribute("entityNotFound", true)
        responseService.registerResponse(model, locale)
      case _ =>
        // user is requiring to become admin of a new  entity: install
        doInstall(registration)
    }
    responseService.loginResponse(model, locale)

  }

  /**
    * Install entity for user requiring to become admin.
    *
    * @param registration
    */
  private def doInstall(registration:Registration) = {
    val userToken = userTokenCommandService.install(registration)
    Option(entityInstaller.installEntity(registration.getCityId, registration.getEntityAlias, registration.getEmail))
      .getOrElse(throw new IllegalArgumentException)
    try {
      registration.isAdmin match {
        case true =>  notificationService.sendWelcome(userToken)
        case false =>  subscriptionService.sendSubscriptionRequestNotification(userToken) // TODO NOT REACHABLE?
      }
    } catch {
      case e:Exception  => {
        e.printStackTrace();
      }
    }
  }

  /**
    * Submit a request to the current admin where user is requiring association to an existing entity.
    *
    * @param registration
    * @param entity
    */
  private def registerPendingUser(registration:Registration, entity: Entity) = {
    val identity = identityInstallService.installIdentity(registration.getEmail)
    val userToken = userTokenCommandService.install(registration)
    var user: User = userInstallService.installUser(entity, registration.getEmail, UserState.PENDING.getValue)
    try {
      subscriptionService.sendSubscriptionRequest(entity, identity)
      subscriptionService.sendSubscriptionRequestNotification(userToken)
    } catch {
      case e:Exception  => {
        e.printStackTrace();
      }
    }
  }

}

package org.helianto.old.service

import java.util.{Date, UUID}
import javax.inject.Inject

import org.helianto.core.domain.{Identity, Lead, Signup}
import org.helianto.core.repository.{IdentityRepository, LeadRepository, SignupRepository}
import org.helianto.security.controller.SecurityNotification
import org.helianto.user.repository.UserRepository
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

/**
  * Signup service.
  *
  * @author mauriciofernandesdecastro
  */
//@Service
class SignUpService //@Inject()
(leadRepository: LeadRepository, identityRepository: IdentityRepository, signupRepository: SignupRepository, userRepository: UserRepository, notificationSender: SecurityNotification){

  val logger: Logger = LoggerFactory.getLogger(classOf[SignUpService])

  /**
    * Verify if email exists
    *
    * @param signup
    **/
  def searchPrincipal(signup: Signup): Boolean = {
    val userList = userRepository.findByIdentityPrincipal(signup.getPrincipal)
    val identity: Identity = identityRepository.findByPrincipal(signup.getPrincipal)
    (identity == null && userList.size == 0)
  }

  /**
    * True if all existing users for the identity are valid, otherwise admin is notified.
    *
    * @param signup
    */
  def allUsersForIdentityAreValid(signup: Signup): Boolean = {
    val userList = userRepository.findByIdentityPrincipal(signup.getPrincipal)
    val isActive: Boolean = userList != null && userList.size > 0
    if (!isActive) {
      return true
    }
    import scala.collection.JavaConversions._
    for (user <- userList) {
      if (!user.isAccountNonLocked || !user.isAccountNonExpired) {
//        notificationSender.send(signup.getPrincipal, signup.getFirstName, signup.getLastName, "Inactive user attempting signup.")
        logger.warn("User {} inactive, notification sent", signup.getPrincipal)
        return false
      }
    }
    return true
  }

  /**
    * True if all existing users for the identity are valid, otherwise admin is notified, except if principal is empty.
    *
    * @param contextId
    * @param principal
    */
  def notifyAdminIfUserIsNotValid(contextId: Integer, principal: String): Boolean = {
    if (principal != null && principal.length > 0) {
      val identity: Identity = identityRepository.findByPrincipal(principal)
      val signup: Signup = new Signup(contextId, principal)
      if (identity != null) {
        signup.setFirstName(identity.getIdentityFirstName)
      }
      return allUsersForIdentityAreValid(signup)
    }
    return false
  }

  /**
    * Save a new lead or return true if existing.
    *
    * @param principal
    */
  def saveLead(principal: String): Boolean = {
    val identity: Identity = identityRepository.findByPrincipal(principal)
    val leads = leadRepository.findByPrincipal(principal)
    if (identity == null && (leads == null || leads.size == 0)) {
      leadRepository.save(new Lead(principal, new Date))
      return false
    }
    return true
  }

  /**
    * Create and save a new signup.
    *
    * @param principal
    */
  def createSignup(principal: String): Signup = {
    val recipient: Identity = identityRepository.findByPrincipal(principal)
    if (recipient != null) {
      var signup: Signup = new Signup(recipient)
      signup.setToken(UUID.randomUUID.toString)
      signup = saveSignup(signup, null)
      return signup
    }
    return null
  }

  /**
    * Create and save a new lead.
    *
    * @param id
    * @param principal
    */
  def createLead(id: Integer, principal: String): Lead = {
    val lead: Lead = new Lead(id, principal, new Date)
    lead.setToken(UUID.randomUUID.toString)
    return leadRepository.saveAndFlush(lead)
  }

  /**
    * Save the signup form.
    *
    * @param signup
    * @param ipAddress
    */
  def saveSignup(signup: Signup, ipAddress: String): Signup = {
    var identity: Identity = identityRepository.findByPrincipal(signup.getPrincipal)
    if (identity == null) {
      identity = identityRepository.saveAndFlush(signup.createIdentityFromForm)
      logger.info("New identity {} created", identity.getPrincipal)
    }
    val exists: Signup = signupRepository.findByContextIdAndPrincipal(1, signup.getPrincipal)
    if (exists != null) {
      exists.setToken(signup.getToken)
      return signupRepository.saveAndFlush(exists)
    }
    signup.setToken(UUID.randomUUID.toString)
    return signupRepository.saveAndFlush(signup)
  }
}
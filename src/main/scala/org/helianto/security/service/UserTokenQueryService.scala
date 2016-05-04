package org.helianto.security.service

import java.util.Date
import javax.inject.Inject

import org.helianto.core.domain.Identity
import org.helianto.core.repository._
import org.helianto.user.domain.UserToken
import org.helianto.user.domain.UserToken.TokenSources
import org.helianto.user.repository.{UserGroupRepository, UserRepository, UserTokenRepository}
import org.joda.time.DateMidnight
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

/**
  * Signup query service.
  *
  * @author mauriciofernandesdecastro
  */
@Service
class UserTokenQueryService @Inject()
(identityRepository: IdentityRepository
 , userTokenRepository: UserTokenRepository
 , userRepository: UserRepository
 , entityInstallService: EntityInstallService
 , entityRepository: EntityRepository
 , keyTypeRepository: KeyTypeRepository
 , operatorRepository: OperatorRepository
 , publicEntityCommandService: PublicEntityCommandService
 , userGroupRepository: UserGroupRepository
){
  def install(registration: Registration) : UserToken = {
    val identity = entityInstallService.installIdentity(registration.getEmail, registration.getPassword)
    if(EntityAliasType.getType(registration.getEntityAlias).equals(EntityAliasType.NUMBER_CODE_TYPE)){
      logger.info("Creating NUMBER_CODE_TYPE  for {}", registration.getEntityAlias)
      Option(keyTypeRepository.findByOperatorAndKeyCode(operatorRepository.findOne(registration.getContextId), "CNPJ")) match {
        case Some(keyType)=>{
          publicEntityCommandService.saveOrUpdatePublicEntityKey(entityRepository.findByOperator_IdAndAlias(registration.getContextId, registration.getEntityAlias).getId, keyType.getId, registration.getEntityAlias)
        }
        case None => logger.info("Cannot create PublicEntityKey without keyType(CNPJ) for {} ", registration.getEntityAlias)
      }
    }
    Option(entityInstallService.install(registration.getContextId, identity, registration)).getOrElse(new UserToken(TokenSources.SIGNUP.name(),registration.getEmail).appendFirstName(identity.getIdentityFirstName))
  }


  val logger: Logger = LoggerFactory.getLogger(classOf[UserTokenQueryService])

  def identityExists(principal:String): Boolean =
    Option(identityRepository.findByPrincipal(principal)) match {
      case Some(identity) if identity.getId >0 => true
      case _ => false
    }

  /**
    * True if all existing users for the identity are valid, otherwise admin is notified.
    *
    * @param userToken
    */
  def allUsersForIdentityAreValid(userToken: UserToken): Boolean = {
    val userList = userRepository.findByIdentityPrincipal(userToken.getPrincipal)
    val isActive: Boolean = userList != null && userList.size > 0
    if (!isActive) {
      return true
    }
    import scala.collection.JavaConversions._
    for (user <- userList) {
      if (!user.isAccountNonLocked || !user.isAccountNonExpired) {
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
      val userToken: UserToken = new UserToken(TokenSources.SIGNUP.name(), identity.getPrincipal)
      if (identity != null) {
        userToken.setFirstName(identity.getIdentityFirstName)
      }
      return allUsersForIdentityAreValid(userToken)
    }
    return false
  }

  /**
    * Create or refresh token.
    *
    * @param principal
    */
  def createOrRefreshToken(principal: String, tokenSource:String): UserToken = {
    Option(identityRepository.findByPrincipal(principal)) match {
      case Some(identity) =>
        Option(userTokenRepository.findByTokenSourceAndPrincipal(tokenSource, principal)) match {
          case Some(userToken) =>
            userToken.setIssueDate(new Date())
            userTokenRepository.saveAndFlush(userToken)
          case _ =>
            userTokenRepository.saveAndFlush(new UserToken(tokenSource, principal).appendFirstName(identity.getDisplayName))
        }
      case None => new UserToken
    }
  }

  def findPreviousSignupAttempts(confirmationToken: String, expirationLimit: Int): Option[Identity] = {
    Option(userTokenRepository.findByToken(confirmationToken)) match {
      case Some(userToken) if (expirationLimit > 0 && userToken.getIssueDate != null) =>
        val expirationDate: DateMidnight = new DateMidnight(userToken.getIssueDate).plusDays(expirationLimit + 1)
        logger.debug("Previous signup attempt valid to {} ", expirationDate)
        if (expirationDate.isAfterNow) {
          Option(identityRepository.findByPrincipal(userToken.getPrincipal))
        } else None
      case _ =>
        logger.debug("Unable to detect any valid previous signup attempt with token {} ", confirmationToken)
        None
    }
  }

  def getPreviousUser(principal: String) =
    Option(identityRepository.findByPrincipal(principal)) match {
      case Some(identity) =>{
        Option( userGroupRepository.findByIdentityIdOrderByLastEventDesc(identity.getId)) match {case Some(list) if list.size()>0=> list.get(0) ; case None => null}
      }
      case None => null
    }


}
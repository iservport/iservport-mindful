package org.helianto.security.service

import javax.inject.Inject

import org.helianto.core.domain.Identity
import org.helianto.core.repository.{IdentityRepository, SignupRepository}
import org.helianto.core.social.UserTokenIdentityAdapter
import org.helianto.install.service.IdentityInstallService
import org.helianto.security.internal.Registration
import org.helianto.user.domain.UserToken
import org.helianto.user.domain.UserToken.TokenSources
import org.helianto.user.repository.UserTokenRepository
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

/**
  * UserToken command service.
  *
  * @author mauriciofernandesdecastro
  */
@Service
class UserTokenCommandService @Inject()
(  identityRepository: IdentityRepository
   , userTokenRepository: UserTokenRepository
   , identityInstallService: IdentityInstallService
   , signupRepository: SignupRepository){

  private val logger: Logger = LoggerFactory.getLogger(classOf[UserTokenCommandService])

  /**
    * Save the signup form and create a Token.
    *
    * @param command
    * @param ipAddress
    */
  def saveOrUpdate(command: UserToken, ipAddress: String): UserToken = {
    //TODO criar campo ip para UserToken
    val identity:Identity = Option(identityRepository.findByPrincipal(command.getPrincipal)) match {
      case Some(id) =>
        logger.warn("Identity {} trying userToken already existing", command.getPrincipal)
        id
      case None =>
        logger.info("New identity {} created", command.getPrincipal)
        val identity = UserTokenIdentityAdapter(command).getIdentityFromUserToken
        identityRepository.saveAndFlush(identity)
    }
    Option(userTokenRepository.findByTokenSourceAndPrincipal(UserToken.TokenSources.SIGNUP.name(), identity.getPrincipal)) match {
      case Some(userToken) => userToken
      case None =>
        logger.info("New userToken for {} created", command.getPrincipal)
        userTokenRepository.saveAndFlush(
          new UserToken(UserToken.TokenSources.SIGNUP, command.getPrincipal).appendFirstName(command.getFirstName))
    }
  }

  def install(registration: Registration) : UserToken = {

    Option(identityInstallService.installIdentity(registration.getEmail)) match {
      case Some(identity) =>
        Option(userTokenRepository.findByTokenSourceAndPrincipal(TokenSources.SIGNUP.name(), identity.getPrincipal)) match {
          case Some(userToken) => userToken
          case None => {
            new UserToken(TokenSources.SIGNUP.name(),registration.getEmail).appendFirstName(identity.getIdentityFirstName)
          }
        }
      case _ => throw new IllegalArgumentException("Unable to install token: registration must have valid e-mail.")
    }
  }

}

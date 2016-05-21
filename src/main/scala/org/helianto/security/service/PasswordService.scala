package org.helianto.security.service

import javax.inject.Inject

import org.helianto.core.domain.Identity
import org.helianto.core.repository.IdentityRepository
import org.helianto.install.service.IdentityCrypto
import org.helianto.security.domain.IdentitySecret
import org.helianto.security.repository.IdentitySecretRepository
import org.helianto.user.repository.UserTokenRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

/**
  * Password service.
  *
  * @author mauriciofernandesdecastro
  */
@Service
class PasswordService @Inject()
( userTokenRepository: UserTokenRepository
  , identityRepository: IdentityRepository
  , identitySecretRepository: IdentitySecretRepository
  , identityCrypto: IdentityCrypto
){

  /**
    * Exchange a token by its corresponding identity.
    *
    * @param confirmationToken
    */
  def getIdentityOption(confirmationToken: String): Option[Identity] = {
    Option(userTokenRepository.findByToken(confirmationToken)) match {
      case Some(userToken) => Option(identityRepository.findByPrincipal(userToken.getPrincipal))
      case None => None
    }
  }

  /**
    * True encrypted password matches the changing one.
    *
    * @param pass
    * @param identity
    */
  def ckeckPassword(pass: String, identity: Identity): Boolean = {
    val identitySecret: IdentitySecret = identitySecretRepository.findByIdentityKey(identity.getPrincipal)
    if (BCrypt.checkpw(pass, identitySecret.getIdentitySecret)) {
      true
    }
    else {
      identityCrypto.changeIdentitySecret(identity.getPrincipal, pass)
      false
    }
  }

  /**
    * Do change password.
    *
    * @param principal
    * @param pass
    */
  def changePassword(principal: String, pass: String) =
    identityCrypto.changeIdentitySecret(principal, pass)

}

package org.helianto.old.service

import javax.inject.Inject

import org.helianto.core.domain.Signup
import org.springframework.social.connect.{ConnectionFactoryLocator, UsersConnectionRepository}
import org.springframework.social.connect.web.ProviderSignInUtils
import org.springframework.stereotype.Service
import org.springframework.web.context.request.WebRequest

/**
  * Created by mauriciofernandesdecastro on 07/03/16.
  */
//@Service
class SignUpSocialService @Inject() (connectionFactoryLocator: ConnectionFactoryLocator,connectionRepository: UsersConnectionRepository) {

  /**
    * Attempt to retrieve signup from social provider.
    *
    * @param contextId
    * @param request
    */
  def socialSignUpAttempt(contextId: Int, request: WebRequest): Signup = {
    val providerSignInUtils: ProviderSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository)
    Option(providerSignInUtils.getConnectionFromSession(request)) match {
      case Some(c) => Option(c.fetchUserProfile) match {
          case Some(p) => new Signup(contextId, p.getEmail, p.getFirstName, p.getLastName)
          case None => new Signup(contextId, "")
        }
      case None => new Signup(contextId, "")
    }
  }

}
package org.helianto.security.service

import java.lang.Boolean

import org.helianto.core.domain.{Entity, Identity}
import org.helianto.install.service.SecurityNotification
import org.helianto.user.domain.UserToken
import org.helianto.user.internal.UserEmailAdapter
import org.springframework.stereotype.Service

/**
  * Created by Eldevan Nery Junior on 13/05/16.
  */
@Service
class MindfulSecurityNotification extends SecurityNotification {

  override def sendWelcome(userToken: UserToken): Boolean = true

  override def sendSignUp(userToken: UserToken): Boolean = true

  override def sendRecovery(userToken: UserToken): Boolean = true

  def sendConfirmation(userConfirmation: UserEmailAdapter): String = ""

}

package org.helianto.security.service

import java.lang.Boolean
import javax.inject.Inject

import org.helianto.core.domain.{Entity, Identity}
import org.helianto.security.SecurityNotification
import org.helianto.user.domain.UserToken
import org.helianto.user.internal.UserEmailAdapter
import org.helianto.user.repository.{EventLogRepository, UserRepository}
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

/**
  * Created by Eldevan Nery Junior on 13/05/16.
  */
@Service
class MindfulSecurityNotification extends SecurityNotification {

  override def sendAdminNotify(entity: Entity, identity: Identity): Boolean = true

  override def sendWelcome(userToken: UserToken): Boolean = true

  override def sendSignUp(userToken: UserToken): Boolean = true

  override def sendRecovery(userToken: UserToken): Boolean = true

  override def sendConfirmation(userConfirmation: UserEmailAdapter): String = ""

}

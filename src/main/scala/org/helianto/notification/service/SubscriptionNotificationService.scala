package org.helianto.notification.service

import org.helianto.core.domain.{Entity, Identity}
import org.helianto.user.domain.{User, UserToken}
import org.helianto.user.internal.UserEmailAdapter
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod, MediaType}
import org.springframework.stereotype.Service

/**
  * Subscription notification service.
  */
@Service
class SubscriptionNotificationService extends AbstractNotificationService {

  /**
    * Sent to Admin User after a subscription request.
    *
    * @param entity
    * @param identity
    */
  def sendSubscriptionRequest(entity: Entity, identity: Identity) = {
    import collection.JavaConversions._
    for { admin:User <- userRepository.findByParentUserType(entity.getId, 'S', Array('A'), null).getContent }
      yield {
        val emailAdapter = new UserEmailAdapter("",senderEmail, senderName, admin.getUserKey, admin.getDisplayName, "", Array())
        val params = Array(
          "template_id", getTemplateId("subscriptionRequest")
          , "entityName", entity.getAlias
          , "candidateDescription", s"${identity.getIdentityName()} (${identity.getPrincipal()}) ")
        buildAndSaveEventLog(emailAdapter, "SIGNUP_REQUEST", "subscriptionRequest")
        emailAdapter.setParams(params)
        sendConfirmation(emailAdapter)
      }
  }

  /**
    * Sent to Candidate User after a subscription request.
    *
    * @param userToken
    *
    */
  def sendSubscriptionRequestNotification(userToken: UserToken) = {
    val emailAdapter = new UserEmailAdapter(apiUrl + "login", senderEmail, senderName
      , userToken.getPrincipal, userToken.getFirstName, Array("template_id", getTemplateId("userApproval")))
    buildAndSaveEventLog(emailAdapter, "LOGIN_APPROVAL", "userApproval")
    sendConfirmation(emailAdapter)
  }

  /**
    * Sent after a subscription approval (or rejection).
    *
    * @param entity
    * @param identity
    */
  // TODO finish implementation
  def sendSubscriptionApproval(entity: Entity, admin:User, identity: Identity, eventType:String ) = {
    val nextAction = eventType match {
      case "SIGNUP_APPROVAL" =>
        Option(userRepository.findByEntity_IdAndIdentity_Id(entity.getId, identity.getId)) match {
          case Some(user) if user.getUserState == 'A' => 1 // aprovado usuário existe e é ativo
          case Some(user) => 2 // aprovado, mas usuário é inativo
          case _ => 3 // aprovado usuário não existe
        }
      case "SIGNUP_DENIAL" => 4 // acesso negado
    }
    val emailAdapter = new UserEmailAdapter("",senderEmail, senderName, admin.getUserPrincipal, admin.getDisplayName, "", Array())
    val params = Array(
      "template_id", getTemplateId("subscriptionRequest")
      , "entityName", entity.getAlias
      , "adminDescription", admin.getIdentity.getDisplayName
      , "nextAction", nextAction)
    buildAndSaveEventLog(emailAdapter, eventType, "subscriptionApproval")
    sendConfirmation(emailAdapter)
  }

  /**
    * Sendgrid based sender.
    *
    * @param userConfirmation
    */
  // TODO implementar
  def sendConfirmation(userConfirmation: UserEmailAdapter): Boolean = true

}

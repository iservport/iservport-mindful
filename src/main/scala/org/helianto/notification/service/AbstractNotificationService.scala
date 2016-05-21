package org.helianto.notification.service

import javax.inject.Inject

import com.fasterxml.jackson.databind.ObjectMapper
import org.helianto.user.domain.{EventLog, UserToken}
import org.helianto.user.internal.UserEmailAdapter
import org.helianto.user.repository.{EventLogRepository, UserRepository}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.env.Environment
import org.springframework.web.util.UriComponentsBuilder

import scala.collection.JavaConversions._

/**
  * Notification base class.
  *
  * @author Eldevan Nery Junior.
  */
abstract class AbstractNotificationService extends InitializingBean {

  @Inject val environment: Environment = null

  @Inject val userRepository: UserRepository = null

  @Inject val eventLogRepository: EventLogRepository = null

  private[notification] val logger: Logger = LoggerFactory.getLogger(classOf[AbstractNotificationService])

  private[notification] var rootUrl: String = null

  private[notification] var senderEmail: String = null

  private[notification] var senderName: String = null

  private[notification] var apiUrl = ""

  private[notification] var templatePrefix = "helianto.sendgrid.template."

  def afterPropertiesSet {
    rootUrl = getRootUrl.build(true).toUriString
    senderEmail = environment.getProperty("sender.noReplyEmail");
    senderName = environment.getProperty("sender.rootFullName")
    apiUrl = environment.getProperty("helianto.api.url")
  }

  /**
    * Create URL to mailer-sender.
    *
    * @return
    */
  def getRootUrl: UriComponentsBuilder =
    UriComponentsBuilder.newInstance.scheme(environment.getProperty("helianto.mailer.scheme", "http"))
      .host(environment.getProperty("helianto.mailer.host", "localhost"))
      .port(environment.getProperty("helianto.mailer.port", "8180"))

  /**
    * Get the template.
    *
    * @param templateName
    */
  def getTemplateId(templateName: String) =
    environment.getRequiredProperty(templatePrefix+templateName)

  /**
    * Get the static URL.
    *
    * @param logUUID
    * @param templateId
    */
  def getBody(logUUID: String, templateId:String): String =
    apiUrl + "static/template/"+getTemplateId(templateId)+"?logId="+logUUID

  /**
    * Access URI
    *
    * @param confirmationToken
    * @param accessPath
    */
  protected  def getConfirmationUri(confirmationToken: String, accessPath: String): String =
    UriComponentsBuilder.fromHttpUrl(apiUrl + accessPath).queryParam("confirmationToken", confirmationToken).build.encode.toUri.toString

  /**
    * Class to Map.
    *
    * @param cc
    */
  def getClassParams(cc: AnyRef) =
    (Map[String, Any]() /: cc.getClass.getDeclaredFields) {(a, f) =>
      f.setAccessible(true)
      a + (f.getName -> f.get(cc))
    }

  /**
    * Partial e-mail adapter builder.
    *
    * @param userToken
    * @param accessPath
    * @param templateId
    */
  def buildPartialUserEmailAdapter(userToken: UserToken, accessPath:String, templateId:String) = {
    val confirmationUri = getConfirmationUri(userToken.getToken, accessPath)
    new UserEmailAdapter(confirmationUri, senderEmail, senderName, userToken.getPrincipal, userToken.getFirstName
      , Array("template_id", getTemplateId(templateId)))
  }

  /**
    * Build and save an event log.
    *
    * @param emailAdapter
    * @param eventType
    * @param templateId
    */
  def buildAndSaveEventLog(emailAdapter: UserEmailAdapter, eventType:String, templateId:String) = {
    val eventLog = new EventLog(eventType)
    emailAdapter.setBody(getBody(eventLog.getLogId, templateId))
    eventLog.setContentAsString(new ObjectMapper().writeValueAsString(mapAsJavaMap(getClassParams(emailAdapter))))
    eventLogRepository.saveAndFlush(eventLog)
  }

}
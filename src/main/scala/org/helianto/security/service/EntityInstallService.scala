package org.helianto.security.service

import javax.inject.Inject

import org.helianto.core.`def`.UserState
import org.helianto.core.domain._
import org.helianto.core.repository._
import org.helianto.install.service.{EntityInstallStrategy, UserInstallService}
import org.helianto.install.service.SecurityNotification
import org.helianto.security.domain.IdentitySecret
import org.helianto.security.internal.Registration
import org.helianto.user.domain.UserToken.TokenSources
import org.helianto.user.domain.{User, UserToken}
import org.helianto.user.repository.{UserRepository, UserTokenRepository}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

@Service
class EntityInstallService @Inject()
(identityRepository: IdentityRepository
 , identityCrypto: IdentityCryptoService
 , leadRepository: LeadRepository
 , contextRepository: OperatorRepository
 , entityRepository: EntityRepository
 , userInstallService: UserInstallService
 , entityInstallStrategy: EntityInstallStrategy
 , signupRepository: SignupRepository
 , userTokenRepository: UserTokenRepository
 , userRepository : UserRepository
 , notificationService: SecurityNotification
) {

  private val logger: Logger = LoggerFactory.getLogger(classOf[EntityInstallService])

  def getSignup(contextId: Integer, identity: Identity): Signup = {
    return signupRepository.findByContextIdAndPrincipal(contextId, identity.getPrincipal)
  }

  def generateEntityPrototypes(registration: Registration): java.util.List[Entity] = {
    return entityInstallStrategy.generateEntityPrototypes(registration)
  }

  def createEntities(context: Operator, prototypes: java.util.List[Entity], identity: Identity, isAdmin: Boolean) {
    import scala.collection.JavaConversions._
    for (prototype <- prototypes) {
      createEntity(context, prototype, identity, isAdmin)
    }
  }

  def createEntity(context: Operator, command: Entity, identity: Identity, isAdmin: Boolean): Entity = {
    val entity: Entity = entityInstallStrategy.installEntity(context, command)
    if (entity != null) {
      createUser(entity, identity, isAdmin)
    }
    return entity
  }



  def createUser(entity: Entity, identity: Identity, isAdmin: Boolean): User = {
    try {
      val principal: String = identity.getPrincipal
      var user: User = userInstallService.installUser(entity, principal)
      if(!isAdmin){
        user = userRepository.findOne(user.getId)
        user.setAccountNonExpired(false)
        user.setUserState(UserState.INACTIVE.getValue)
        user = userRepository.saveAndFlush(user)
        try{ notificationService.sendAdminNotify(entity, identity)}  catch { case e:Exception=>e.printStackTrace() }
      }
      removeLead(principal)
      return user
    }
    catch {
      case e: Exception => {
        e.printStackTrace
        return null
      }
    }
  }

  final def removeLead(leadPrincipal: String): String = {
    val leads: java.util.List[Lead] = leadRepository.findByPrincipal(leadPrincipal)
    import scala.collection.JavaConversions._
    for (lead <- leads) {
      leadRepository.delete(lead)
    }
    return leadPrincipal
  }

  //TODO change to option case
  def installIdentity(email:String, password:String):Identity = {
    var identity: Identity = identityRepository.findByPrincipal(email)
    if(identity == null) {
      logger.info("Will install identity for {}.", email);
      identity = identityRepository.saveAndFlush(new Identity(email));
    } else {
      logger.debug("Found existing identity for {}.", email);
    }
    var identitySecret: IdentitySecret = identityCrypto.getIdentitySecretByPrincipal(identity.getPrincipal)
    if (identitySecret == null) {
      logger.info("Will install identity secret for {}.", identity)
      identitySecret = identityCrypto.createIdentitySecret(identity, password, false)
    }
    else {
      logger.info("Will change identity secret for {}.", identity)
      identitySecret = identityCrypto.changeIdentitySecret(identity.getPrincipal, password)
    }
    identity
  }

  def entityOption(contextId:Int, entityAlias:String): Option[Entity] = {
    val context: Operator = contextRepository.findOne(contextId)
    Option(entityRepository.findByContextNameAndAlias(context.getOperatorName, entityAlias))
  }

  def install(contextId:Int, identity:Identity, registration: Registration) = {
    //TODO ver se é usuário é admin e cadastrar ou entao notificar
    val context: Operator = contextRepository.findOne(contextId)
    val userToken: UserToken = userTokenRepository.findByTokenSourceAndPrincipal(TokenSources.SIGNUP.name(), identity.getPrincipal)
    val prototypes: java.util.List[Entity] = generateEntityPrototypes(registration)
    createEntities(context, prototypes, identity, registration.isAdmin)

    userToken
  }
}
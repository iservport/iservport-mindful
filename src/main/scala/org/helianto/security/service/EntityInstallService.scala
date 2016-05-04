package org.helianto.security.service

import javax.inject.Inject

import org.helianto.core.domain._
import org.helianto.core.repository._
import org.helianto.install.service.{EntityInstallStrategy, UserInstallService}
import org.helianto.security.domain.IdentitySecret
import org.helianto.user.domain.User
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
 , signupRepository: SignupRepository) {

  private val logger: Logger = LoggerFactory.getLogger(classOf[EntityInstallService])

  def getSignup(contextId: Integer, identity: Identity): Signup = {
    return signupRepository.findByContextIdAndPrincipal(contextId, identity.getPrincipal)
  }

  def generateEntityPrototypes(signup: Signup): java.util.List[Entity] = {
    return entityInstallStrategy.generateEntityPrototypes(signup)
  }

  def createEntities(context: Operator, prototypes: java.util.List[Entity], identity: Identity) {
    import scala.collection.JavaConversions._
    for (prototype <- prototypes) {
      createEntity(context, prototype, identity)
    }
  }

  def createEntity(context: Operator, command: Entity, identity: Identity): Entity = {
    val entity: Entity = entityInstallStrategy.installEntity(context, command)
    if (entity != null) {
      createUser(entity, identity)
    }
    return entity
  }

  def createUser(entity: Entity, identity: Identity): User = {
    try {
      val principal: String = identity.getPrincipal
      val user: User = userInstallService.installUser(entity, principal)
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

  def installIdentity(email:String,password:String):Identity = {
    val identity: Identity = identityRepository.findByPrincipal(email)
    logger.debug("User {} exists", identity.getPrincipal)
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

  def install(contextId:Int, identity:Identity, entityAlias:String) = {
    val context: Operator = contextRepository.findOne(contextId)
    val entity: Entity = entityRepository.findByContextNameAndAlias(context.getOperatorName, entityAlias)
    val signup: Signup = signupRepository.findByContextIdAndPrincipal(contextId, identity.getPrincipal)
    signup.setDomain(entityAlias)
    val prototypes: java.util.List[Entity] = generateEntityPrototypes(signup)
    createEntities(context, prototypes, identity)
    signup
  }
}
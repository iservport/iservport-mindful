package com.iservport.mindful.service

import javax.inject.Inject

import org.helianto.core.domain.{City, Entity}
import org.helianto.core.install.EntityInstaller
import org.helianto.core.repository.{CityRepository, EntityRepository, OperatorRepository}
import org.helianto.install.service.UserInstallService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

@Service
class CityAsEntityInstaller extends EntityInstaller {

  @Inject private val contextRepository: OperatorRepository = null
  @Inject private val cityRepository: CityRepository = null
  @Inject private val entityRepository: EntityRepository = null
  @Inject private val userInstallService: UserInstallService = null
  @Inject private val authorityInstaller: AuthorityInstaller = null

  private val logger: Logger = LoggerFactory.getLogger(classOf[CityAsEntityInstaller])

  def installEntity(cityId: Int, alias: String, principal: String): Entity = {
    val city: City = cityRepository.findOne(cityId)
    if (city == null) {
      logger.error("Unable to find city with id {}.", cityId)
      throw new IllegalArgumentException("Unable to find city to create entity")
    }
    val forcedAlias = city.getCityCode
    val context = contextRepository.findOne(city.getContext.getId)
    val entity = Option(entityRepository.findByOperator_IdAndAlias(context.getId, forcedAlias)) match {
      case Some(e) => e
      case _ =>
        logger.info("New entity for context {} and alias {}.", city.getContext.getId, forcedAlias)
        entityRepository.saveAndFlush(new Entity(city, forcedAlias))
    }
    userInstallService.installUser(entity, principal)
    authorityInstaller.installAuthorities(entity, principal)
    logger.debug("Admin User {} created .", principal)
    entity
  }

}
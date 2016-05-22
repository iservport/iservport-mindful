package com.iservport.mindful.service

import javax.inject.Inject

import com.iservport.mindful.repository.EntityByCityRepository
import org.helianto.core.domain.Entity
import org.helianto.core.install.EntityInstaller
import org.helianto.core.repository.{CityRepository, OperatorRepository}
import org.helianto.install.service.UserInstallService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

@Service
class CityAsEntityInstaller extends EntityInstaller {

  @Inject private val cityRepository: CityRepository = null
  @Inject private val entityByCityRepository: EntityByCityRepository = null
  @Inject private val userInstallService: UserInstallService = null
  @Inject private val authorityInstaller: AuthorityInstaller = null

  private val logger: Logger = LoggerFactory.getLogger(classOf[CityAsEntityInstaller])

  def installEntity(cityId: Int, alias: String, principal: String): Entity = {
    val entity = Option(entityByCityRepository.findByCityId(cityId)) match {
      case Some(e) => e
      case _ =>
        val city = cityRepository.findOne(cityId)
        logger.info("New entity for city {}.", city)
        entityByCityRepository.saveAndFlush(new Entity(city, city.getCityCode))
    }
    userInstallService.installUser(entity, principal)
    authorityInstaller.installAuthorities(entity, principal)
    logger.debug("Admin User {} created .", principal)
    entity
  }

}
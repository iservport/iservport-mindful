package com.iservport.mindful.service

import org.helianto.core.domain.Entity
import org.helianto.security.domain.UserAuthority
import org.helianto.security.repository.UserAuthorityRepository
import org.helianto.user.domain.UserGroup
import org.helianto.user.repository.UserGroupRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.inject.Inject

/**
  * Authority installer.
  */
@Service
class AuthorityInstaller {

  @Inject private val userGroupRepository: UserGroupRepository = null
  @Inject private val userAuthorityRepository: UserAuthorityRepository = null

  def installAuthorities(entity: Entity, principal: String) {
    val admin: UserGroup = userGroupRepository.findByEntity_IdAndUserKey(entity.getId, "ADMIN")
    if (admin == null) {
      throw new IllegalArgumentException("Unable to install context, ADMIN group not found; check your contextGroups definition to ensure proper installation.")
    }
    val rootAuthority: UserAuthority = installAuthority(admin, "ADMIN", "READ,WRITE")
    val userAuthority: UserAuthority = installAuthority(admin, "USER", "READ,WRITE")
  }

  private def installAuthority(userGroup: UserGroup, serviceName: String, serviceExtension: String): UserAuthority = {
    Option(userAuthorityRepository.findByUserGroupAndServiceCode(userGroup, serviceName)) match {
      case Some(authority) => authority
      case _ =>
        val authority = new UserAuthority(userGroup, serviceName)
        authority.setServiceExtension(serviceExtension)
        userAuthorityRepository.saveAndFlush(authority)
    }
  }

}
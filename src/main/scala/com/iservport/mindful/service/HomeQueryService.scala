package com.iservport.mindful.service

import javax.inject.Inject
import org.helianto.core.internal.QualifierAdapter
import org.helianto.core.repository.EntityReadAdapter
import org.helianto.core.repository.EntityRepository
import org.helianto.user.domain.User
import org.helianto.user.repository.UserReadAdapter
import org.helianto.user.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.ArrayList
import java.util.List

/**
  * Home query service.
  */
@Service
class HomeQueryService {

  @Inject private val entityRepository: EntityRepository = null
  @Inject private val userRepository: UserRepository = null

  // TODO
  def qualifier(entityId: Int) =
    new java.util.ArrayList[QualifierAdapter]

  def entity(entityId: Int) =
    entityRepository.findAdapter(entityId)

  def user(userId: Int) =
    userRepository.findAdapter(userId)

}
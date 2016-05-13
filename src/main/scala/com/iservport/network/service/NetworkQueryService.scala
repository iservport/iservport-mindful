//package com.iservport.network.service
//
//import com.iservport.network.repository.NetworkRepository
//import org.helianto.core.domain.Entity
//import org.helianto.core.internal.{KeyNameAdapter, QualifierAdapter}
//import org.helianto.security.internal.UserAdapter
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Pageable
//import org.springframework.data.domain.Sort.Direction
//import javax.inject.Inject
//
//import org.springframework.stereotype.Service
//
///**
//  * Network query service.
//  */
//@Service
//class NetworkQueryService {
//
//  @Inject private val rootRepository: NetworkRepository = null
//
//  def qualifierList(entityId: Int): java.util.List[QualifierAdapter] =
//    QualifierAdapter.qualifierAdapterList(MindfulNetworkKeyName.values.map(v => v.asInstanceOf[KeyNameAdapter]))
//
//  def listUser(identityId: Int, entityType: Char, pageNumber: Integer, itemsPerPage: Integer): Page[UserAdapter] = {
//    val page: Pageable = new PageRequest(pageNumber, itemsPerPage, Direction.DESC, "lastEvent")
//    rootRepository.findByIdentityIdAndEntityTypeOrderByLastEventDesc(identityId, entityType, 'A', 'A', page)
//  }
//
//  def search(entityId: Integer, searchString: String, pageNumber: Integer): Page[UserAdapter] = {
//    val paged: Pageable = new PageRequest(pageNumber, 10, Direction.DESC, "issueDate")
//    rootRepository.findByIdentityIdAndEntityOrderByLastEventDesc(entityId, "%" + searchString + "%", paged)
//  }
//
//  def entity(userId: Int): UserAdapter = {
//    val entity: Entity = rootRepository.findByUserId(userId)
//    new UserAdapter(userId, entity)
//  }
//
//}
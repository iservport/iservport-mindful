//package com.iservport.network.repository
//
//import org.helianto.core.domain.Entity
//import org.helianto.security.internal.UserAdapter
//import org.helianto.user.domain.User
//import org.springframework.data.domain.{Page, Pageable}
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
//
///**
//  * Network repository.
//  */
//object NetworkQuery {
//  final val q = "select new " +
//    "org.helianto.security.internal.UserAdapter" +
//    "(child.id, child.entity) " +
//    "from User child " +
//    "join child.parentAssociations parents " +
//    "where lower(parents.parent.userKey) = 'user' " +
//    "and child.identity.id = ?1 "
//}
//trait NetworkRepository extends JpaRepository[User, Integer] {
//
//  @Query(NetworkQuery.q + "and parents.parent.entity.entityType = ?2 "
//    + "and child.entity.activityState = ?3 "
//    + "and child.userState = ?4 "
//    + "order by child.lastEvent DESC ")
//  def findByIdentityIdAndEntityTypeOrderByLastEventDesc(identityId: Int, entityType: Char, c: Char, c1: Char, page: Pageable): Page[UserAdapter]
//
//  @Query(NetworkQuery.q + "and parents.parent.entity.alias LIKE ?2 "
//    + "order by child.lastEvent DESC ")
//  def findByIdentityIdAndEntityOrderByLastEventDesc(entityId: Integer, s: String, paged: Pageable): Page[UserAdapter]
//
//  @Query("select user.entity "
//    + "from User user "
//    + "where user.id = ?1 ")
//  def findByUserId(userId: Int): Entity
//
//}
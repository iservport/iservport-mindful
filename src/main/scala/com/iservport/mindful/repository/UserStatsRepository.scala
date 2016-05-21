package com.iservport.mindful.repository

import java.io.Serializable
import java.util.Date
import java.util.List
import org.helianto.core.internal.SimpleCounter
import org.helianto.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

trait UserStatsRepository extends JpaRepository[User, Serializable] {

  @Query("select new " +
    "org.helianto.core.internal.SimpleCounter" +
    "(parents.parent.userType, count(user)) " +
    "from User user " +
    "join user.parentAssociations parents " +
    "where user.entity.id = ?1 " +
    "and user.userState = 'A' " +
    "group by parents.parent.userType ")
  def countActiveUsersGroupByType(entityId: Int): java.util.List[SimpleCounter]

  @Query("select new " +
    "org.helianto.core.internal.SimpleCounter" +
    "(parents.parent.userType, count(user)) " +
    "from User user " +
    "join user.parentAssociations parents " +
    "where user.entity.id = ?1 " +
    "and user.userState = 'A' " +
    "group by parents.parent.userType ")
  def countUsersGroupByType(entityId: Int, checkDate: Date): java.util.List[SimpleCounter]

}
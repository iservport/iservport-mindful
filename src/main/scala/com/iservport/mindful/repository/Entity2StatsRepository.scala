package com.iservport.mindful.repository

import org.helianto.core.domain.Entity
import org.helianto.core.internal.SimpleCounter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.List

trait Entity2StatsRepository extends JpaRepository[Entity, Integer] {

  @Query("select new " + "org.helianto.core.internal.SimpleCounter" +
    "(entity.entityType, count(entity)) " +
    "from Entity entity " +
    "where entity.operator.id = ?1 " +
    "and entity.activityState = 'A' " +
    "group by entity.entityType ")
  def countActiveEntitiesGroupByType(contextId: Int): java.util.List[SimpleCounter]

}
package com.iservport.mindful.repository

import org.helianto.core.domain.Entity
import org.springframework.data.jpa.repository.{JpaRepository, Query}

/**
  * Entity custom repository.
  */
trait EntityByCityRepository  extends JpaRepository[Entity, Integer] {

  @Query("select e_ from Entity e_ " +
    "where e_.city.id = ?1 and e_.alias = e_.city.cityCode ")
  def findByCityId(cityId: Int): Entity

}

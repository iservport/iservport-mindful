package com.iservport.mindful.repository

import java.io.Serializable
import java.util.List
import org.helianto.core.domain.ContextGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

trait ContextGroupTempRepository extends JpaRepository[ContextGroup, Serializable] {

  @Query("select contextGroup " +
    "from ContextGroup contextGroup " +
    "where contextGroup.context.id = ?1 " +
    "and contextGroup.contextGroupCode = ?2 ")
  def findByContextIdAndContextGroupCode(contextId: Int, contextGroupCode: String): ContextGroup

  def findById(id: Integer): java.util.List[ContextGroup]

}
package com.iservport.mindful.repository

import com.iservport.mindful.domain.Vote
import org.helianto.core.internal.SimpleCounter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.io.Serializable
import java.util.List

trait VoteRepository extends JpaRepository[Vote, Serializable] {

  @Query("select v_.voto " +
    "from Vote v_ " +
    "where v_.documento.id = ?1 " +
    "and v_.eleitor.id = ?2 ")
  def findByDocumentoLegislativoIdAndUserId(documentId: Integer, userId: Integer): Integer

  @Query("select new " +
    "org.helianto.core.internal.SimpleCounter" +
    "(v_.voto, count(v_)) " +
    "from Vote v_ " +
    "where v_.documento.id = ?1 " +
    "group by v_.voto ")
  def countVotosByDocumentoLegislativoId(id: Integer): java.util.List[SimpleCounter]

  @Query("select new " +
    "org.helianto.core.internal.SimpleCounter" +
    "(v_.voto, count(v_)) " +
    "from Vote v_ " +
    "where v_.documento.id = ?1 " +
    "and v_.voto = ?2 " +
    "group by v_.voto ")
  def countByDocumentoLegislativoId(id: Integer, voteType: Integer): SimpleCounter
}
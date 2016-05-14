package com.iservport.mindful.service

import javax.inject.Inject

import org.helianto.core.internal.SimpleCounter
import org.springframework.stereotype.Service
import com.iservport.mindful.repository.{LegalDocumentReadAdapter, VoteDetails, VoteRepository}
import java.util.List

import com.iservport.mindful.domain.LegalDocument

@Service class VoteQueryService {
  @Inject protected var votoRepository: VoteRepository = null

  def getDocumentoVoto(documentoId: Integer, userId: Integer): Integer = {
    votoRepository.findByDocumentoLegislativoIdAndUserId(documentoId, userId)
  }

  def getVoteSimpleCountByType(documentId: Integer, voteType: Integer): SimpleCounter = {
    votoRepository.countByDocumentoLegislativoId(documentId, voteType)
  }

  def getVoteSimpleCountByType(documentoId: Integer) = {
    votoRepository.countVotosByDocumentoLegislativoId(documentoId)
  }

  def getVoteDetails(documentId: Integer): VoteDetails = {
    val disagree: Long = votoRepository.countByDocumentoLegislativoId(documentId, 0).getItemCount
    val agree: Long = votoRepository.countByDocumentoLegislativoId(documentId, 1).getItemCount
    new VoteDetails(agree, disagree, disagree + agree)
  }


  // TODO-vote-query-service duplicated ?
  def getVote(userId: Int, documentId:Int) = {
    val canVote = Option(votoRepository.findByDocumentoLegislativoIdAndUserId(documentId, userId)) match {
      case Some(count) if count>=0 => false
      case _ => true
    }
  }

}
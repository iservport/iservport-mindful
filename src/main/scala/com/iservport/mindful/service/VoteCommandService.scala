package com.iservport.mindful.service

import javax.inject.Inject

import org.helianto.user.repository.UserRepository
import org.springframework.stereotype.Service
import com.iservport.mindful.domain.Vote
import com.iservport.mindful.repository.{LegalDocumentReadAdapter, LegalDocumentRepository, VoteRepository}

@Service class VoteCommandService {

  @Inject private val documentoLegislativoRepository: LegalDocumentRepository = null
  @Inject private val userRepository: UserRepository = null
  @Inject private val voteRepository: VoteRepository = null

  def vote(documentoId: Integer, userId: Integer, voto: Integer): LegalDocumentReadAdapter = {
    val voted: Vote = new Vote(documentoLegislativoRepository.findOne(documentoId), userRepository.findOne(userId))
    voted.setVoto(voto)
    voteRepository.saveAndFlush(voted)
    documentoLegislativoRepository.findById(documentoId)
  }

}
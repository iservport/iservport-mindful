package com.iservport.mindful.service

import javax.inject.Inject

import com.iservport.mindful.repository.LegalDocumentRepository
import org.springframework.stereotype.Service

/**
  * Document query service.
  */
@Service
class DocumentQueryService {

  @Inject private val documentoLegislativoRepository: LegalDocumentRepository = null

  def getTotalDocuments(resolution: Character, entityId: Integer) =
    documentoLegislativoRepository.countDocumentoLegislativoByResolution(resolution, entityId)

  def getDocuments(entityId: Integer) =
    documentoLegislativoRepository.findByEntityId(entityId)

  def getDocument(id: Integer) =
    documentoLegislativoRepository.findById(id)

}
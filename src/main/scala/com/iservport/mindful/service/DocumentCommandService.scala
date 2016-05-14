package com.iservport.mindful.service

import com.iservport.mindful.domain.LegalDocument
import org.springframework.stereotype.Service

/**
  * Document command service.
  */
@Service
class DocumentCommandService {

  def saveOrUpdate(entityId: Int, ownerId: Int, command: LegalDocument) = {
    // TODO-Document-command save or update
    new LegalDocument()
  }

}
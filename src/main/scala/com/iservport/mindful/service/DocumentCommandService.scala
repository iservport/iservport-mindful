package com.iservport.mindful.service

import javax.inject.Inject

import com.iservport.mindful.domain.LegalDocument
import com.iservport.mindful.repository.LegalDocumentRepository
import org.helianto.core.repository.EntityRepository
import org.springframework.stereotype.Service

/**
  * Document command service.
  */
@Service
class DocumentCommandService @Inject()(legalDocumentRepository: LegalDocumentRepository, entityRepository: EntityRepository){

  /**
    * Create Legal doc
    *
    * @param entityId
    *
    */
  def create(entityId: Int) = new LegalDocument(entityRepository.findOne(entityId), "");

  /**
    * SaveOrUpdate
    *
    * @param entityId
    * @param ownerId
    * @param command
    *
    */
  def saveOrUpdate(entityId: Int, ownerId: Int, command: LegalDocument) = {
    val target = Option(legalDocumentRepository.findOne(command.getId)) match {
      case Some(ld) => ld
        //TODO add Parlamentar author?
      case _ => new LegalDocument(entityRepository.findOne(command.getEntityId), command.getDocCode)
    }
    legalDocumentRepository.saveAndFlush(target.merge(command))
  }


}
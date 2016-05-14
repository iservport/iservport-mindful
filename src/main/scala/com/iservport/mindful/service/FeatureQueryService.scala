package com.iservport.mindful.service

import com.iservport.mindful.repository.{FeatureRepository, LegalDocumentRepository}
import org.helianto.core.domain.Feature
import org.helianto.core.internal.SimpleCounter
import org.springframework.stereotype.Service
import javax.inject.Inject
import java.util.List

/**
  * Feature query service.
  */
@Service
class FeatureQueryService {

  @Inject private val featureRepository: FeatureRepository = null

  def getFeatureCounters(contextId: Integer) =
    featureRepository.countFeatureByContextId(contextId)

}
package com.iservport.mindful.repository;

import org.helianto.core.domain.Feature;
import org.helianto.core.internal.SimpleCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Feature repository.
 */
public interface FeatureTmpRepository extends JpaRepository<Feature, Integer> {

    /**
     * Find feature
     *
     * @param contextId
     */
    @Query("select new "
            + "org.helianto.core.internal.SimpleCounter"
            + "(feature.featureCode, count(doc)) "
            + "from Feature feature, LegalDocument doc "
            + "where feature.context.id = ?1 and doc.feature.id = feature.id "
            + "group by doc.feature.id ")
    List<SimpleCounter> countFeatureByContextId(int contextId);

}

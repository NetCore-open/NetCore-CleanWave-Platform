package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.repositories;

import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities.PlanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link PlanPersistenceEntity}.
 */
@Repository
public interface PlanPersistenceRepository extends JpaRepository<PlanPersistenceEntity, Long> {
    /**
     * Checks if a plan with the given name exists.
     *
     * @param name the plan name to check
     * @return {@code true} if a plan with that name exists, {@code false} otherwise
     */
    boolean existsByName(String name);
}

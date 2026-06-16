package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.repositories;

import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link SubscriptionPersistenceEntity}.
 */
@Repository
public interface SubscriptionPersistenceRepository extends JpaRepository<SubscriptionPersistenceEntity, Long> {
    /**
     * Retrieves all subscriptions by laundry.
     *
     * @param laundryId the laundry identifier
     * @return the list of subscriptions
     */
    List<SubscriptionPersistenceEntity> findByLaundryId(Long laundryId);
}

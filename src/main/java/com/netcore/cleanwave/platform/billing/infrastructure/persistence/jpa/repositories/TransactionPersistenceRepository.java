package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.repositories;

import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities.TransactionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link TransactionPersistenceEntity}.
 */
@Repository
public interface TransactionPersistenceRepository extends JpaRepository<TransactionPersistenceEntity, Long> {
    /**
     * Retrieves all transactions logged under a subscription.
     *
     * @param subscriptionId the subscription identifier
     * @return the list of transactions
     */
    List<TransactionPersistenceEntity> findBySubscriptionId(Long subscriptionId);
}

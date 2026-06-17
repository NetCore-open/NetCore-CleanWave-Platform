package com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.repositories;

import com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.entities.DeliveryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link DeliveryPersistenceEntity}.
 */
@Repository
public interface DeliveryPersistenceRepository extends JpaRepository<DeliveryPersistenceEntity, Long> {
    /**
     * Retrieves all deliveries for a specific user.
     *
     * @param userId the customer identifier
     * @return the list of delivery persistence entities
     */
    List<DeliveryPersistenceEntity> findByUserId(Long userId);
}

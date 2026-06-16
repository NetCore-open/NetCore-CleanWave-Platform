package com.netcore.cleanwave.platform.logistics.domain.repositories;

import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for managing {@link Delivery} aggregates.
 */
public interface DeliveryRepository {
    /**
     * Persists the given delivery aggregate.
     *
     * @param delivery the delivery aggregate to save
     * @return the saved delivery aggregate
     */
    Delivery save(Delivery delivery);

    /**
     * Retrieves a delivery by its unique identifier.
     *
     * @param id the unique identifier of the delivery
     * @return an {@link Optional} containing the delivery if found, empty otherwise
     */
    Optional<Delivery> findById(Long id);

    /**
     * Retrieves all registered deliveries.
     *
     * @return the list of all deliveries
     */
    List<Delivery> findAll();

    /**
     * Retrieves all deliveries associated with a specific user.
     *
     * @param userId the user identifier
     * @return the list of deliveries matching the user
     */
    List<Delivery> findByUserId(Long userId);

    /**
     * Deletes a delivery by its unique identifier.
     *
     * @param id the unique identifier of the delivery to delete
     */
    void deleteById(Long id);
}

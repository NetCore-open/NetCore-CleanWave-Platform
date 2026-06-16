package com.netcore.cleanwave.platform.billing.domain.repositories;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for managing {@link Subscription} aggregates.
 */
public interface SubscriptionRepository {
    /**
     * Persists the given subscription aggregate.
     *
     * @param subscription the subscription to save
     * @return the saved subscription aggregate
     */
    Subscription save(Subscription subscription);

    /**
     * Retrieves a subscription by its unique identifier.
     *
     * @param id the unique identifier of the subscription
     * @return an {@link Optional} containing the subscription if found, empty otherwise
     */
    Optional<Subscription> findById(Long id);

    /**
     * Retrieves all subscriptions associated with a specific laundry.
     *
     * @param laundryId the unique identifier of the laundry
     * @return the list of subscriptions for the laundry
     */
    List<Subscription> findByLaundryId(Long laundryId);
}

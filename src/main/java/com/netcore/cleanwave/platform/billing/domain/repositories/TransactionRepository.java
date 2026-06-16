package com.netcore.cleanwave.platform.billing.domain.repositories;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for managing {@link Transaction} aggregates.
 */
public interface TransactionRepository {
    /**
     * Persists the given payment transaction aggregate.
     *
     * @param transaction the transaction to save
     * @return the saved transaction aggregate
     */
    Transaction save(Transaction transaction);

    /**
     * Retrieves a transaction by its unique identifier.
     *
     * @param id the unique identifier of the transaction
     * @return an {@link Optional} containing the transaction if found, empty otherwise
     */
    Optional<Transaction> findById(Long id);

    /**
     * Retrieves all transactions associated with a specific subscription.
     *
     * @param subscriptionId the unique identifier of the subscription
     * @return the list of transactions for the subscription
     */
    List<Transaction> findBySubscriptionId(Long subscriptionId);
}

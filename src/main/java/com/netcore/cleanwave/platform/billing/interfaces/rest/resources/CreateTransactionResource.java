package com.netcore.cleanwave.platform.billing.interfaces.rest.resources;

/**
 * REST request body resource containing parameters to create a new transaction.
 */
public record CreateTransactionResource(
        Long subscriptionId,
        double amount
) {
}

package com.netcore.cleanwave.platform.billing.interfaces.rest.resources;

/**
 * REST response resource representation for a transaction.
 */
public record TransactionResource(
        Long id,
        Long subscriptionId,
        double amount,
        String date,
        String status
) {
}

package com.netcore.cleanwave.platform.billing.domain.model.commands;

/**
 * Command record to request the registration of a new payment transaction.
 *
 * @param subscriptionId the unique identifier of the target subscription
 * @param amount         the total monetary amount paid
 */
public record CreateTransactionCommand(Long subscriptionId, double amount) {
}

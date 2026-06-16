package com.netcore.cleanwave.platform.billing.domain.model.queries;

/**
 * Query record to request all transactions associated with a given subscription.
 *
 * @param subscriptionId the unique identifier of the subscription
 */
public record GetTransactionsBySubscriptionQuery(Long subscriptionId) {
}

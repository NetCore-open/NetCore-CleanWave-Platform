package com.netcore.cleanwave.platform.billing.domain.model.commands;

/**
 * Command record to request the cancellation of an existing subscription.
 *
 * @param subscriptionId the unique identifier of the subscription to cancel
 */
public record CancelSubscriptionCommand(Long subscriptionId) {
}

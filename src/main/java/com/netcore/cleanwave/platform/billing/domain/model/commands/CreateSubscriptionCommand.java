package com.netcore.cleanwave.platform.billing.domain.model.commands;

/**
 * Command record to request the creation of a new laundry subscription.
 *
 * @param planId    the ID of the target subscription plan
 * @param laundryId the ID of the laundry performing the subscription
 */
public record CreateSubscriptionCommand(Long planId, Long laundryId) {
}

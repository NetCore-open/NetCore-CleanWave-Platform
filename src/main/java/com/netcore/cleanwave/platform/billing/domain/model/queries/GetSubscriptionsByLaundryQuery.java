package com.netcore.cleanwave.platform.billing.domain.model.queries;

/**
 * Query record to request all subscriptions associated with a given laundry.
 *
 * @param laundryId the unique identifier of the laundry
 */
public record GetSubscriptionsByLaundryQuery(Long laundryId) {
}

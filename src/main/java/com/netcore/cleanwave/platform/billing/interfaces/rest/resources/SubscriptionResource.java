package com.netcore.cleanwave.platform.billing.interfaces.rest.resources;

/**
 * REST response resource representation for a laundry subscription.
 */
public record SubscriptionResource(
        Long id,
        Long planId,
        Long laundryId,
        String status,
        String startDate,
        String endDate
) {
}

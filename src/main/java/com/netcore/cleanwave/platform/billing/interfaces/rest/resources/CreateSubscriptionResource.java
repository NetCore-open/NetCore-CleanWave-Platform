package com.netcore.cleanwave.platform.billing.interfaces.rest.resources;

/**
 * REST request body resource containing parameters to create a new subscription.
 */
public record CreateSubscriptionResource(
        Long planId,
        Long laundryId
) {
}

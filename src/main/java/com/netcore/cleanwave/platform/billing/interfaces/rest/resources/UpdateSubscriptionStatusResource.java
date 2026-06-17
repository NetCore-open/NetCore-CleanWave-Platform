package com.netcore.cleanwave.platform.billing.interfaces.rest.resources;

/**
 * REST request body resource to update a subscription's status (e.g. to CANCELLED).
 */
public record UpdateSubscriptionStatusResource(
        String status
) {
}

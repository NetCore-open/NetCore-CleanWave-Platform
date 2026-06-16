package com.netcore.cleanwave.platform.logistics.interfaces.rest.resources;

import org.jspecify.annotations.Nullable;

/**
 * REST request body resource to request creation of a new delivery.
 */
public record CreateDeliveryResource(
        Long orderId,
        Long userId,
        String type,
        String address,
        String scheduledDate,
        @Nullable String notes
) {
}

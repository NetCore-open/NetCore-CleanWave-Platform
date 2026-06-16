package com.netcore.cleanwave.platform.logistics.interfaces.rest.resources;

import org.jspecify.annotations.Nullable;

/**
 * REST request body resource to update a delivery's status and details.
 */
public record UpdateDeliveryStatusResource(
        String status,
        @Nullable String driverName,
        @Nullable String driverPhone
) {
}

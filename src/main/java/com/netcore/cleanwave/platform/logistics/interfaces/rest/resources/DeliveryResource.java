package com.netcore.cleanwave.platform.logistics.interfaces.rest.resources;

import org.jspecify.annotations.Nullable;

/**
 * REST response resource representation for a delivery task.
 */
public record DeliveryResource(
        Long id,
        Long orderId,
        Long userId,
        String type,
        String status,
        String address,
        String scheduledDate,
        @Nullable String driverName,
        @Nullable String driverPhone,
        @Nullable String notes,
        String createdAt,
        String updatedAt
) {
}

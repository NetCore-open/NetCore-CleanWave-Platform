package com.netcore.cleanwave.platform.logistics.domain.model.commands;

import com.netcore.cleanwave.platform.logistics.domain.model.valueobjects.DeliveryStatus;
import org.jspecify.annotations.Nullable;

/**
 * Command record to request updating a delivery's status and logistics details.
 *
 * @param id          the unique identifier of the delivery to update
 * @param status      the new delivery status
 * @param driverName  the assigned driver's name (can be null)
 * @param driverPhone the assigned driver's phone number (can be null)
 */
public record UpdateDeliveryStatusCommand(
        Long id,
        DeliveryStatus status,
        @Nullable String driverName,
        @Nullable String driverPhone
) {
}

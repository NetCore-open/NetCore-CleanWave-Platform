package com.netcore.cleanwave.platform.logistics.domain.model.commands;

import org.jspecify.annotations.Nullable;
import java.time.LocalDate;

/**
 * Command record to request the registration of a new logistics task.
 *
 * @param orderId       the unique identifier of the target order
 * @param userId        the unique identifier of the customer
 * @param type          the type of operation (PICKUP or DELIVERY)
 * @param address       the destination address
 * @param scheduledDate the scheduled date for the operation
 * @param notes         custom delivery instructions (can be null)
 */
public record CreateDeliveryCommand(
        Long orderId,
        Long userId,
        String type,
        String address,
        LocalDate scheduledDate,
        @Nullable String notes
) {
}

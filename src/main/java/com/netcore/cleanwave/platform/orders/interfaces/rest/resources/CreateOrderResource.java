package com.netcore.cleanwave.platform.orders.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

public record CreateOrderResource(
        Long userId,
        Long laundryId,
        String address,
        LocalDate scheduledPickup,
        String notes,
        List<CreateOrderItemResource> items
) {
}

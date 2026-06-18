package com.netcore.cleanwave.platform.orders.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

public record OrderResource(
        Long id,
        Long userId,
        Long laundryId,
        String status,
        String address,
        LocalDate scheduledPickup,
        String notes,
        List<OrderItemResource> items
) {
}

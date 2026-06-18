package com.netcore.cleanwave.platform.orders.domain.model.commands;

import java.time.LocalDate;
import java.util.List;

public record CreateOrderCommand(
        Long userId,
        Long laundryId,
        String address,
        LocalDate scheduledPickup,
        String notes,
        List<CreateOrderItemCommand> items
) {
}

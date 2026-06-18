package com.netcore.cleanwave.platform.orders.domain.model.commands;

import com.netcore.cleanwave.platform.orders.domain.model.valueobjects.OrderStatus;

public record UpdateOrderStatusCommand(
        Long orderId,
        OrderStatus status
) {
}

package com.netcore.cleanwave.platform.orders.domain.model.commands;

public record CreateOrderItemCommand(
        String garmentType,
        Integer quantity,
        Double unitPrice
) {
}

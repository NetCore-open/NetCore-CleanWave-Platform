package com.netcore.cleanwave.platform.orders.interfaces.rest.resources;

public record OrderItemResource(
        Long id,
        String garmentType,
        Integer quantity,
        Double unitPrice
) {
}

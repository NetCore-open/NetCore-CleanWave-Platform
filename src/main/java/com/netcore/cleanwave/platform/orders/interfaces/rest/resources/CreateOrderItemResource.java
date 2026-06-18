package com.netcore.cleanwave.platform.orders.interfaces.rest.resources;

import java.util.List;

public record CreateOrderItemResource(
        String garmentType,
        Integer quantity,
        Double unitPrice
) {
}

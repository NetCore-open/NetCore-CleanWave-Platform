package com.netcore.cleanwave.platform.orders.interfaces.rest.transform;

import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;
import com.netcore.cleanwave.platform.orders.interfaces.rest.resources.OrderItemResource;
import com.netcore.cleanwave.platform.orders.interfaces.rest.resources.OrderResource;

import java.util.stream.Collectors;

public class OrderResourceFromEntityAssembler {

    public static OrderResource toResourceFromEntity(Order entity) {
        return new OrderResource(
                entity.getId(),
                entity.getUserId(),
                entity.getLaundryId(),
                entity.getStatus().name(),
                entity.getAddress(),
                entity.getScheduledPickup(),
                entity.getNotes(),
                entity.getItems().stream()
                        .map(item -> new OrderItemResource(
                                item.getId(),
                                item.getGarmentType(),
                                item.getQuantity(),
                                item.getUnitPrice()
                        ))
                        .collect(Collectors.toList())
        );
    }
}

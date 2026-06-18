package com.netcore.cleanwave.platform.orders.interfaces.rest.transform;

import com.netcore.cleanwave.platform.orders.domain.model.commands.CreateOrderCommand;
import com.netcore.cleanwave.platform.orders.domain.model.commands.CreateOrderItemCommand;
import com.netcore.cleanwave.platform.orders.interfaces.rest.resources.CreateOrderResource;

import java.util.stream.Collectors;

public class CreateOrderCommandFromResourceAssembler {

    public static CreateOrderCommand toCommandFromResource(CreateOrderResource resource) {
        return new CreateOrderCommand(
                resource.userId(),
                resource.laundryId(),
                resource.address(),
                resource.scheduledPickup(),
                resource.notes(),
                resource.items().stream()
                        .map(item -> new CreateOrderItemCommand(
                                item.garmentType(),
                                item.quantity(),
                                item.unitPrice()
                        ))
                        .collect(Collectors.toList())
        );
    }
}

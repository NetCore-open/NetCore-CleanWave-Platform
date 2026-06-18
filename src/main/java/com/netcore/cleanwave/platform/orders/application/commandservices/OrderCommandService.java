package com.netcore.cleanwave.platform.orders.application.commandservices;

import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;
import com.netcore.cleanwave.platform.orders.domain.model.commands.CreateOrderCommand;
import com.netcore.cleanwave.platform.orders.domain.model.commands.UpdateOrderStatusCommand;

import java.util.Optional;

public interface OrderCommandService {
    Long handle(CreateOrderCommand command);
    Optional<Order> handle(UpdateOrderStatusCommand command);
}

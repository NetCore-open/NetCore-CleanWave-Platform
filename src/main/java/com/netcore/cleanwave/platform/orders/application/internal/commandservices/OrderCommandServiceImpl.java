package com.netcore.cleanwave.platform.orders.application.internal.commandservices;

import com.netcore.cleanwave.platform.orders.application.commandservices.OrderCommandService;
import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;
import com.netcore.cleanwave.platform.orders.domain.model.commands.CreateOrderCommand;
import com.netcore.cleanwave.platform.orders.domain.model.commands.UpdateOrderStatusCommand;
import com.netcore.cleanwave.platform.orders.domain.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;

    public OrderCommandServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Long handle(CreateOrderCommand command) {
        Order order = new Order(command);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Optional<Order> handle(UpdateOrderStatusCommand command) {
        Optional<Order> orderOptional = orderRepository.findById(command.orderId());
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.updateStatus(command.status());
            orderRepository.save(order);
            return Optional.of(order);
        }
        return Optional.empty();
    }
}

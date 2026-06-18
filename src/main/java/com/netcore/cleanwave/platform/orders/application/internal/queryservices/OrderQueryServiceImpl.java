package com.netcore.cleanwave.platform.orders.application.internal.queryservices;

import com.netcore.cleanwave.platform.orders.application.queryservices.OrderQueryService;
import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetAllOrdersQuery;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetOrderByIdQuery;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetOrdersByUserQuery;
import com.netcore.cleanwave.platform.orders.domain.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    public OrderQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> handle(GetAllOrdersQuery query) {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> handle(GetOrdersByUserQuery query) {
        return orderRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<Order> handle(GetOrderByIdQuery query) {
        return orderRepository.findById(query.orderId());
    }
}

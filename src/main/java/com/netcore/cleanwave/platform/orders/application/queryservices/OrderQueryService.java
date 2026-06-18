package com.netcore.cleanwave.platform.orders.application.queryservices;

import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetAllOrdersQuery;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetOrderByIdQuery;
import com.netcore.cleanwave.platform.orders.domain.model.queries.GetOrdersByUserQuery;

import java.util.List;
import java.util.Optional;

public interface OrderQueryService {
    List<Order> handle(GetAllOrdersQuery query);
    List<Order> handle(GetOrdersByUserQuery query);
    Optional<Order> handle(GetOrderByIdQuery query);
}

package com.netcore.cleanwave.platform.orders.domain.repositories;

import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findByUserId(Long userId);
}

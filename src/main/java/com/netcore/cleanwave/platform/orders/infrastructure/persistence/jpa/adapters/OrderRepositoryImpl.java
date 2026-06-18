package com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;
import com.netcore.cleanwave.platform.orders.domain.repositories.OrderRepository;
import com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.assemblers.OrderPersistenceEntityAssembler;
import com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.entities.OrderPersistenceEntity;
import com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.repositories.OrderPersistenceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderPersistenceRepository orderPersistenceRepository;

    public OrderRepositoryImpl(OrderPersistenceRepository orderPersistenceRepository) {
        this.orderPersistenceRepository = orderPersistenceRepository;
    }

    @Override
    public void save(Order order) {
        OrderPersistenceEntity entity = OrderPersistenceEntityAssembler.toPersistenceEntity(order);
        // Note: when saving an updated entity with nested collections, it's simpler to rely on JPA cascade.
        OrderPersistenceEntity savedEntity = orderPersistenceRepository.save(entity);
        order.setId(savedEntity.getId());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderPersistenceRepository.findById(id)
                .map(OrderPersistenceEntityAssembler::toDomainAggregate);
    }

    @Override
    public List<Order> findAll() {
        return orderPersistenceRepository.findAll().stream()
                .map(OrderPersistenceEntityAssembler::toDomainAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderPersistenceRepository.findByUserId(userId).stream()
                .map(OrderPersistenceEntityAssembler::toDomainAggregate)
                .collect(Collectors.toList());
    }
}

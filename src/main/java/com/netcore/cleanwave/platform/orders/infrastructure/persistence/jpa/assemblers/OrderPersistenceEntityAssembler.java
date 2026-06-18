package com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.orders.domain.model.aggregates.Order;
import com.netcore.cleanwave.platform.orders.domain.model.entities.OrderItem;
import com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.entities.OrderItemPersistenceEntity;
import com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.entities.OrderPersistenceEntity;

import java.util.stream.Collectors;

public class OrderPersistenceEntityAssembler {

    public static OrderPersistenceEntity toPersistenceEntity(Order order) {
        OrderPersistenceEntity entity = new OrderPersistenceEntity(
                order.getUserId(),
                order.getLaundryId(),
                order.getStatus(),
                order.getAddress(),
                order.getScheduledPickup(),
                order.getNotes()
        );
        
        if (order.getId() != null) {
            entity.setId(order.getId());
        }

        for (OrderItem item : order.getItems()) {
            OrderItemPersistenceEntity itemEntity = new OrderItemPersistenceEntity(
                    item.getGarmentType(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    entity
            );
            if (item.getId() != null) {
                itemEntity.setId(item.getId());
            }
            entity.addItem(itemEntity);
        }

        return entity;
    }

    public static Order toDomainAggregate(OrderPersistenceEntity entity) {
        Order order = new Order(
                entity.getId(),
                entity.getUserId(),
                entity.getLaundryId(),
                entity.getStatus(),
                entity.getItems().stream()
                        .map(itemEntity -> new OrderItem(
                                itemEntity.getId(),
                                itemEntity.getGarmentType(),
                                itemEntity.getQuantity(),
                                itemEntity.getUnitPrice()
                        ))
                        .collect(Collectors.toList()),
                entity.getAddress(),
                entity.getScheduledPickup(),
                entity.getNotes()
        );
        return order;
    }
}

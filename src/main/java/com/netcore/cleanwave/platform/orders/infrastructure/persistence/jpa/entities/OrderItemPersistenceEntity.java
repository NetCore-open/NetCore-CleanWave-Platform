package com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
@Entity
@Table(name = "order_items")
public class OrderItemPersistenceEntity extends AuditableAbstractPersistenceEntity {

    private String garmentType;
    private Integer quantity;
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderPersistenceEntity order;

    protected OrderItemPersistenceEntity() {
    }

    public OrderItemPersistenceEntity(String garmentType, Integer quantity, Double unitPrice, OrderPersistenceEntity order) {
        this.garmentType = Objects.requireNonNull(garmentType, "garmentType must not be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity must not be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice must not be null");
        this.order = Objects.requireNonNull(order, "order must not be null");
    }

    public String getGarmentType() {
        return garmentType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public OrderPersistenceEntity getOrder() {
        return order;
    }

    public void setOrder(OrderPersistenceEntity order) {
        this.order = order;
    }
}

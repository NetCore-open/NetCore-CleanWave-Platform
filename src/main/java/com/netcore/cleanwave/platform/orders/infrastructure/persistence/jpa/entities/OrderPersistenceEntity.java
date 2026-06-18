package com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.orders.domain.model.valueobjects.OrderStatus;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NullMarked
@Entity
@Table(name = "orders")
public class OrderPersistenceEntity extends AuditableAbstractPersistenceEntity {

    private Long userId;
    private Long laundryId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String address;
    private LocalDate scheduledPickup;

    @Nullable
    private String notes;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemPersistenceEntity> items = new ArrayList<>();

    protected OrderPersistenceEntity() {
    }

    public OrderPersistenceEntity(Long userId, Long laundryId, OrderStatus status, String address, LocalDate scheduledPickup, @Nullable String notes) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.laundryId = Objects.requireNonNull(laundryId, "laundryId must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.address = Objects.requireNonNull(address, "address must not be null");
        this.scheduledPickup = Objects.requireNonNull(scheduledPickup, "scheduledPickup must not be null");
        this.notes = notes;
    }

    public void addItem(OrderItemPersistenceEntity item) {
        items.add(item);
        item.setOrder(this);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getLaundryId() {
        return laundryId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getScheduledPickup() {
        return scheduledPickup;
    }

    @Nullable
    public String getNotes() {
        return notes;
    }

    public List<OrderItemPersistenceEntity> getItems() {
        return items;
    }
}

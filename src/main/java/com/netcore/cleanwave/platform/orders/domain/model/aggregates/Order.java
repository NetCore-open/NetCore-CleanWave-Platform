package com.netcore.cleanwave.platform.orders.domain.model.aggregates;

import com.netcore.cleanwave.platform.orders.domain.model.commands.CreateOrderCommand;
import com.netcore.cleanwave.platform.orders.domain.model.entities.OrderItem;
import com.netcore.cleanwave.platform.orders.domain.model.valueobjects.OrderStatus;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Aggregate root for the Orders bounded context.
 */
@NullMarked
public class Order extends AbstractDomainAggregateRoot<Order> {
    private Long id;
    private Long userId;
    private Long laundryId;
    private OrderStatus status;
    private List<OrderItem> items;
    private String address;
    private LocalDate scheduledPickup;

    @Nullable
    private String notes;

    public Order(Long id, Long userId, Long laundryId, OrderStatus status, List<OrderItem> items,
                 String address, LocalDate scheduledPickup, @Nullable String notes) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.laundryId = Objects.requireNonNull(laundryId, "laundryId must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.items = Objects.requireNonNull(items, "items must not be null");
        this.address = Objects.requireNonNull(address, "address must not be null");
        this.scheduledPickup = Objects.requireNonNull(scheduledPickup, "scheduledPickup must not be null");
        this.notes = notes;
    }

    public Order(CreateOrderCommand command) {
        this(null, command.userId(), command.laundryId(), OrderStatus.PENDING,
                command.items().stream()
                        .map(item -> new OrderItem(item.garmentType(), item.quantity(), item.unitPrice()))
                        .collect(Collectors.toList()),
                command.address(), command.scheduledPickup(), command.notes());
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = Objects.requireNonNull(newStatus, "newStatus must not be null");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderItem> getItems() {
        return items;
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
}

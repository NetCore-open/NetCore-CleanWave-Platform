package com.netcore.cleanwave.platform.orders.domain.model.entities;

import org.jspecify.annotations.NullMarked;
import java.util.Objects;

/**
 * Entity representing a specific item (garment) within an order.
 *
 * <p>It is not an aggregate root; it belongs to an {@code Order}.</p>
 */
@NullMarked
public class OrderItem {
    private Long id;
    private String garmentType;
    private Integer quantity;
    private Double unitPrice;

    public OrderItem(Long id, String garmentType, Integer quantity, Double unitPrice) {
        this.id = id;
        this.garmentType = Objects.requireNonNull(garmentType, "garmentType must not be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity must not be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice must not be null");
    }

    public OrderItem(String garmentType, Integer quantity, Double unitPrice) {
        this(null, garmentType, quantity, unitPrice);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getSubtotal() {
        return this.quantity * this.unitPrice;
    }
}

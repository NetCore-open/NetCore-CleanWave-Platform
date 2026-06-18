package com.netcore.cleanwave.platform.orders.domain.model.valueobjects;

/**
 * Value object enum representing the various states of an order.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PICKED_UP,
    IN_PROCESS,
    READY,
    DELIVERED,
    CANCELLED
}

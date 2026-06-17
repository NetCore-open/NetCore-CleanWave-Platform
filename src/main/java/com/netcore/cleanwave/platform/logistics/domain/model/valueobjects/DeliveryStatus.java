package com.netcore.cleanwave.platform.logistics.domain.model.valueobjects;

/**
 * Value object enum representing the various states of a delivery.
 */
public enum DeliveryStatus {
    PENDING,
    ASSIGNED,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED,
    FAILED
}

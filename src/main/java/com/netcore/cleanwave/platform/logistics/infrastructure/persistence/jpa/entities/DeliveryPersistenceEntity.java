package com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.logistics.domain.model.valueobjects.DeliveryStatus;
import com.netcore.cleanwave.platform.logistics.domain.model.valueobjects.DeliveryType;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

/**
 * JPA persistence entity for the
 * {@link com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery}
 * domain aggregate.
 */
@Entity
@Table(name = "deliveries")
@Getter
@Setter
public class DeliveryPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(nullable = false)
    private String address;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @Column(name = "driver_name")
    @Nullable
    private String driverName;

    @Column(name = "driver_phone")
    @Nullable
    private String driverPhone;

    @Column(columnDefinition = "TEXT")
    @Nullable
    private String notes;

    public DeliveryPersistenceEntity() {
    }

    public DeliveryPersistenceEntity(Long orderId, Long userId, DeliveryType type, DeliveryStatus status,
                                     String address, LocalDate scheduledDate, @Nullable String driverName,
                                     @Nullable String driverPhone, @Nullable String notes) {
        this.orderId = orderId;
        this.userId = userId;
        this.type = type;
        this.status = status;
        this.address = address;
        this.scheduledDate = scheduledDate;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.notes = notes;
    }
}

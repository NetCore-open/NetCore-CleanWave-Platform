package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.billing.domain.model.valueobjects.SubscriptionStatus;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the
 * {@link com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription}
 * domain aggregate.
 */
@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class SubscriptionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(name = "laundry_id", nullable = false)
    private Long laundryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public SubscriptionPersistenceEntity() {
    }

    public SubscriptionPersistenceEntity(Long planId, Long laundryId, SubscriptionStatus status,
                                         LocalDate startDate, LocalDate endDate) {
        this.planId = planId;
        this.laundryId = laundryId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

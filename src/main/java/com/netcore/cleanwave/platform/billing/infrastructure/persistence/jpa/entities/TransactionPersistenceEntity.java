package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.billing.domain.model.valueobjects.TransactionStatus;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the
 * {@link com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction}
 * domain aggregate.
 */
@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "subscription_id", nullable = false)
    private Long subscriptionId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    public TransactionPersistenceEntity() {
    }

    public TransactionPersistenceEntity(Long subscriptionId, double amount, LocalDate date, TransactionStatus status) {
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
}

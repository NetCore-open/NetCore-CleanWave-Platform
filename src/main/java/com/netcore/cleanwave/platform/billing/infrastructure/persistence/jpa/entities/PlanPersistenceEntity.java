package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.billing.domain.model.valueobjects.PlanType;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.converters.StringListConverter;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA persistence entity for the
 * {@link com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan}
 * domain aggregate.
 */
@Entity
@Table(name = "plans")
@Getter
@Setter
public class PlanPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType type;

    @Column(name = "billing_period", nullable = false)
    private String billingPeriod;

    @Convert(converter = StringListConverter.class)
    @Column(name = "laundry_features", length = 1000)
    private List<String> laundryFeatures = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "client_features", length = 1000)
    private List<String> clientFeatures = new ArrayList<>();

    @Column(nullable = false)
    private boolean recommended;

    public PlanPersistenceEntity() {
    }

    public PlanPersistenceEntity(String name, double price, PlanType type, String billingPeriod,
                                 List<String> laundryFeatures, List<String> clientFeatures, boolean recommended) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.billingPeriod = billingPeriod;
        this.laundryFeatures = laundryFeatures != null ? laundryFeatures : new ArrayList<>();
        this.clientFeatures = clientFeatures != null ? clientFeatures : new ArrayList<>();
        this.recommended = recommended;
    }
}

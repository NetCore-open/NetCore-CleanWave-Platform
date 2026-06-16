package com.netcore.cleanwave.platform.billing.domain.model.aggregates;

import com.netcore.cleanwave.platform.billing.domain.model.valueobjects.PlanType;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root for the Billing bounded context representing a subscription plan.
 *
 * <p>Holds the plan details like price, type, billing period, and lists of features
 * available for both laundries and clients, as well as whether the plan is recommended.</p>
 *
 * <p>JPA persistence concerns are kept out of this class; they live in the
 * infrastructure layer's {@code PlanPersistenceEntity}.</p>
 */
@NullMarked
public class Plan extends AbstractDomainAggregateRoot<Plan> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private String name;

    @Getter
    private double price;

    @Getter
    private PlanType type;

    @Getter
    private String billingPeriod;

    @Getter
    private List<String> laundryFeatures;

    @Getter
    private List<String> clientFeatures;

    @Getter
    private boolean recommended;

    /**
     * Default constructor. Initialises feature lists to empty arrays.
     */
    public Plan() {
        this.laundryFeatures = new ArrayList<>();
        this.clientFeatures = new ArrayList<>();
    }

    /**
     * Creates a subscription plan with all primitive values.
     *
     * @param name            the plan's display name
     * @param price           the cost of the plan
     * @param type            the plan type (FREE, PREMIUM, ANNUAL)
     * @param billingPeriod   the duration/cycle of billing (e.g., "monthly", "yearly")
     * @param laundryFeatures features available for laundries under this plan
     * @param clientFeatures  features available for clients under this plan
     * @param recommended     whether this plan is highlighted as recommended
     */
    public Plan(String name, double price, PlanType type, String billingPeriod,
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

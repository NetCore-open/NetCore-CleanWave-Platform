package com.netcore.cleanwave.platform.billing.domain.model.aggregates;

import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateSubscriptionCommand;
import com.netcore.cleanwave.platform.billing.domain.model.valueobjects.SubscriptionStatus;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;

/**
 * Aggregate root for the Billing bounded context representing a laundry subscription to a plan.
 *
 * <p>Holds references to the plan and the laundry, alongside the subscription's
 * start date, end date, and current operational status.</p>
 */
@NullMarked
public class Subscription extends AbstractDomainAggregateRoot<Subscription> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private Long planId;

    @Getter
    private Long laundryId;

    @Getter
    private SubscriptionStatus status;

    @Getter
    private LocalDate startDate;

    @Getter
    private LocalDate endDate;

    /**
     * Default constructor. Initialises status to {@link SubscriptionStatus#ACTIVE}.
     */
    public Subscription() {
        this.status = SubscriptionStatus.ACTIVE;
    }

    /**
     * Creates a subscription from its individual attributes.
     *
     * @param planId    the unique identifier of the selected plan
     * @param laundryId the unique identifier of the laundry subscribing to the plan
     * @param startDate the start date of the subscription
     * @param endDate   the end date of the subscription
     */
    public Subscription(Long planId, Long laundryId, LocalDate startDate, LocalDate endDate) {
        this.planId = planId;
        this.laundryId = laundryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = SubscriptionStatus.ACTIVE;
    }

    /**
     * Creates a subscription from a {@link CreateSubscriptionCommand}.
     *
     * @param command the command containing creation data
     */
    public Subscription(CreateSubscriptionCommand command) {
        this.planId = command.planId();
        this.laundryId = command.laundryId();
        this.startDate = LocalDate.now();
        // Default subscription duration is 30 days unless specified otherwise (or annual plan)
        this.endDate = LocalDate.now().plusDays(30);
        this.status = SubscriptionStatus.ACTIVE;
    }

    /**
     * Cancels this subscription, moving its status to {@link SubscriptionStatus#CANCELLED}.
     *
     * @return this aggregate instance (fluent API)
     */
    public Subscription cancel() {
        this.status = SubscriptionStatus.CANCELLED;
        return this;
    }
}

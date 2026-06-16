package com.netcore.cleanwave.platform.billing.application.internal.commandservices;

import com.netcore.cleanwave.platform.billing.application.commandservices.SubscriptionCommandService;
import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;
import com.netcore.cleanwave.platform.billing.domain.model.commands.CancelSubscriptionCommand;
import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateSubscriptionCommand;
import com.netcore.cleanwave.platform.billing.domain.repositories.PlanRepository;
import com.netcore.cleanwave.platform.billing.domain.repositories.SubscriptionRepository;
import com.netcore.cleanwave.platform.laundries.domain.repositories.LaundryRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that handles subscription write operations.
 *
 * <p>Implements {@link SubscriptionCommandService} to orchestrate creation
 * and cancellation of {@link Subscription} aggregates. Verifies plan and laundry existence
 * prior to persisting subscription changes.</p>
 */
@NullMarked
@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final LaundryRepository laundryRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository,
                                           PlanRepository planRepository,
                                           LaundryRepository laundryRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.laundryRepository = laundryRepository;
    }

    /**
     * Handles the creation of a new laundry subscription.
     *
     * @param command the command containing subscription parameters
     * @return a success {@link Result} with the created subscription, or a failure {@link Result} with an error
     */
    @Override
    public Result<Subscription, ApplicationError> handle(CreateSubscriptionCommand command) {
        var planOpt = planRepository.findById(command.planId());
        if (planOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Plan", "Plan not found with ID: " + command.planId()));
        }

        var laundryOpt = laundryRepository.findById(command.laundryId());
        if (laundryOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Laundry", "Laundry not found with ID: " + command.laundryId()));
        }

        try {
            var subscription = new Subscription(command);
            var savedSubscription = subscriptionRepository.save(subscription);
            return Result.success(savedSubscription);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Subscription creation", e.getMessage()));
        }
    }

    /**
     * Handles the cancellation of an existing subscription.
     *
     * @param command the command containing the subscription identifier
     * @return a success {@link Result} with the updated subscription, or a failure {@link Result} with an error
     */
    @Override
    public Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command) {
        var subscriptionOpt = subscriptionRepository.findById(command.subscriptionId());
        if (subscriptionOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", "Subscription not found with ID: " + command.subscriptionId()));
        }

        try {
            var subscription = subscriptionOpt.get();
            subscription.cancel();
            var savedSubscription = subscriptionRepository.save(subscription);
            return Result.success(savedSubscription);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Subscription cancellation", e.getMessage()));
        }
    }
}

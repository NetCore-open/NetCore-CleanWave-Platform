package com.netcore.cleanwave.platform.billing.application.commandservices;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;
import com.netcore.cleanwave.platform.billing.domain.model.commands.CancelSubscriptionCommand;
import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateSubscriptionCommand;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;

/**
 * Application service interface for processing commands related to {@link Subscription} aggregates.
 */
@NullMarked
public interface SubscriptionCommandService {
    /**
     * Handles the command to create a new subscription.
     *
     * @param command the command containing subscription details
     * @return a {@link Result} carrying the created subscription or an error
     */
    Result<Subscription, ApplicationError> handle(CreateSubscriptionCommand command);

    /**
     * Handles the command to cancel an existing subscription.
     *
     * @param command the command containing target subscription id
     * @return a {@link Result} carrying the updated/cancelled subscription or an error
     */
    Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command);
}

package com.netcore.cleanwave.platform.logistics.application.commandservices;

import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;
import com.netcore.cleanwave.platform.logistics.domain.model.commands.CreateDeliveryCommand;
import com.netcore.cleanwave.platform.logistics.domain.model.commands.DeleteDeliveryCommand;
import com.netcore.cleanwave.platform.logistics.domain.model.commands.UpdateDeliveryStatusCommand;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;

/**
 * Application service interface for processing commands related to {@link Delivery} aggregates.
 */
@NullMarked
public interface DeliveryCommandService {
    /**
     * Handles the command to create a new logistics task.
     *
     * @param command the command containing task details
     * @return a {@link Result} carrying the created delivery or an error
     */
    Result<Delivery, ApplicationError> handle(CreateDeliveryCommand command);

    /**
     * Handles the command to update the status and driver details of a delivery.
     *
     * @param command the command containing update details
     * @return a {@link Result} carrying the updated delivery or an error
     */
    Result<Delivery, ApplicationError> handle(UpdateDeliveryStatusCommand command);

    /**
     * Handles the command to delete a logistics task.
     *
     * @param command the command containing target delivery ID
     * @return a {@link Result} carrying confirmation of success or an error
     */
    Result<Void, ApplicationError> handle(DeleteDeliveryCommand command);
}

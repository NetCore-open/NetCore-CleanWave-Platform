package com.netcore.cleanwave.platform.notifications.application.commandservices;

import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.DeleteNotificationCommand;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;

/**
 * Application service interface for processing commands related to {@link Notification} aggregates.
 */
@NullMarked
public interface NotificationCommandService {
    /**
     * Handles the command to dispatch a new notification.
     *
     * @param command the command containing notification parameters
     * @return a {@link Result} carrying the created notification or an error
     */
    Result<Notification, ApplicationError> handle(CreateNotificationCommand command);

    /**
     * Handles the command to mark a notification as read.
     *
     * @param command the command containing the target notification ID
     * @return a {@link Result} carrying the updated notification or an error
     */
    Result<Notification, ApplicationError> handle(MarkNotificationAsReadCommand command);

    /**
     * Handles the command to delete a notification.
     *
     * @param command the command containing the target notification ID
     * @return a {@link Result} indicating success or an error
     */
    Result<Void, ApplicationError> handle(DeleteNotificationCommand command);
}

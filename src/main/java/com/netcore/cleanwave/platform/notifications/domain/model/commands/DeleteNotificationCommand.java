package com.netcore.cleanwave.platform.notifications.domain.model.commands;

/**
 * Command record to request deleting a notification.
 *
 * @param id the unique identifier of the notification to delete
 */
public record DeleteNotificationCommand(
        Long id
) {
}

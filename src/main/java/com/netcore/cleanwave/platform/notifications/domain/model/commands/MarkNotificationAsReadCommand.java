package com.netcore.cleanwave.platform.notifications.domain.model.commands;

/**
 * Command record to request marking a notification as read.
 *
 * @param id the unique identifier of the notification to mark
 */
public record MarkNotificationAsReadCommand(
        Long id
) {
}

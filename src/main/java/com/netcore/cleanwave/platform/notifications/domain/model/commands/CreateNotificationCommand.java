package com.netcore.cleanwave.platform.notifications.domain.model.commands;

import org.jspecify.annotations.Nullable;

/**
 * Command record to request dispatching a new notification.
 *
 * @param userId   the unique identifier of the target user
 * @param title    the header title text
 * @param message  the main description body text
 * @param type     the category classification (ORDER, LOGISTICS, BILLING, SYSTEM, PROMO)
 * @param priority the priority classification (LOW, MEDIUM, HIGH)
 * @param link     an optional redirection URI (can be null)
 */
public record CreateNotificationCommand(
        Long userId,
        String title,
        String message,
        String type,
        String priority,
        @Nullable String link
) {
}

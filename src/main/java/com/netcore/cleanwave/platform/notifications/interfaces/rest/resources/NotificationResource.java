package com.netcore.cleanwave.platform.notifications.interfaces.rest.resources;

import org.jspecify.annotations.Nullable;

/**
 * REST response resource representation for a notification.
 */
public record NotificationResource(
        Long id,
        Long userId,
        String title,
        String message,
        String type,
        String priority,
        boolean isRead,
        @Nullable String link
) {
}

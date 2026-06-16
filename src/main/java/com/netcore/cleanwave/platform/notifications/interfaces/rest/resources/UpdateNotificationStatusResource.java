package com.netcore.cleanwave.platform.notifications.interfaces.rest.resources;

/**
 * REST request body resource to update a notification's read status.
 */
public record UpdateNotificationStatusResource(
        boolean isRead
) {
}

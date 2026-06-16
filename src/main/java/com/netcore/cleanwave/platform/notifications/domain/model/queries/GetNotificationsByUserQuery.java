package com.netcore.cleanwave.platform.notifications.domain.model.queries;

/**
 * Query record to request all notifications logged for a specific user.
 *
 * @param userId the unique identifier of the customer
 */
public record GetNotificationsByUserQuery(
        Long userId
) {
}

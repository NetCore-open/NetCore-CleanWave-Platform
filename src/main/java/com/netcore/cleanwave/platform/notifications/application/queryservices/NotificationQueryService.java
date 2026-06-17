package com.netcore.cleanwave.platform.notifications.application.queryservices;

import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;
import com.netcore.cleanwave.platform.notifications.domain.model.queries.GetNotificationsByUserQuery;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Application service interface for processing queries related to {@link Notification} aggregates.
 */
@NullMarked
public interface NotificationQueryService {
    /**
     * Handles the query to retrieve all notifications registered for a specific user.
     *
     * @param query the query containing the target user ID
     * @return the list of notifications matching the user ID
     */
    List<Notification> handle(GetNotificationsByUserQuery query);
}

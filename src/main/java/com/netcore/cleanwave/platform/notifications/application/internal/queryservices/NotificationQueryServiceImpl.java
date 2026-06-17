package com.netcore.cleanwave.platform.notifications.application.internal.queryservices;

import com.netcore.cleanwave.platform.notifications.application.queryservices.NotificationQueryService;
import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;
import com.netcore.cleanwave.platform.notifications.domain.model.queries.GetNotificationsByUserQuery;
import com.netcore.cleanwave.platform.notifications.domain.repositories.NotificationRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementation that handles notification queries.
 *
 * <p>Implements {@link NotificationQueryService} to retrieve notification records.</p>
 */
@NullMarked
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetNotificationsByUserQuery query) {
        return notificationRepository.findByUserId(query.userId());
    }
}

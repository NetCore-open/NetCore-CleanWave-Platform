package com.netcore.cleanwave.platform.notifications.interfaces.rest.transform;

import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;
import com.netcore.cleanwave.platform.notifications.interfaces.rest.resources.NotificationResource;

/**
 * Assembler that converts a {@link Notification} domain aggregate into a
 * {@link NotificationResource} REST representation.
 */
public class NotificationResourceFromEntityAssembler {

    /**
     * Converts a {@link Notification} domain aggregate to a {@link NotificationResource}.
     *
     * @param entity the notification aggregate to convert
     * @return the corresponding REST response resource
     */
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getType().name(),
                entity.getPriority().name(),
                entity.isRead(),
                entity.getLink()
        );
    }
}

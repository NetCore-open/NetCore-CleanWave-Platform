package com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;
import com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;

/**
 * Assembler that converts between {@link Notification} domain aggregates and
 * {@link NotificationPersistenceEntity} JPA persistence entities.
 */
public class NotificationPersistenceAssembler {

    /**
     * Converts a {@link NotificationPersistenceEntity} to a {@link Notification} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static Notification toDomainFromPersistence(NotificationPersistenceEntity entity) {
        var domain = new Notification(
                entity.getUserId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getType(),
                entity.getPriority(),
                entity.getLink()
        );
        domain.setId(entity.getId());
        if (entity.isRead()) {
            domain.markAsRead();
        }
        return domain;
    }

    /**
     * Converts a {@link Notification} domain aggregate to a {@link NotificationPersistenceEntity}.
     *
     * @param domain the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static NotificationPersistenceEntity toPersistenceFromDomain(Notification domain) {
        var entity = new NotificationPersistenceEntity(
                domain.getUserId(),
                domain.getTitle(),
                domain.getMessage(),
                domain.getType(),
                domain.getPriority(),
                domain.isRead(),
                domain.getLink()
        );
        entity.setId(domain.getId());
        return entity;
    }
}

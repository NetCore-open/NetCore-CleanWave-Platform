package com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.notifications.domain.model.valueobjects.NotificationPriority;
import com.netcore.cleanwave.platform.notifications.domain.model.valueobjects.NotificationType;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

/**
 * JPA persistence entity for the
 * {@link com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification}
 * domain aggregate.
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
public class NotificationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationPriority priority;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column
    @Nullable
    private String link;

    public NotificationPersistenceEntity() {
    }

    public NotificationPersistenceEntity(Long userId, String title, String message,
                                         NotificationType type, NotificationPriority priority,
                                         boolean isRead, @Nullable String link) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = priority;
        this.isRead = isRead;
        this.link = link;
    }
}

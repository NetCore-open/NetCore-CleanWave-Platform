package com.netcore.cleanwave.platform.notifications.domain.model.aggregates;

import com.netcore.cleanwave.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.netcore.cleanwave.platform.notifications.domain.model.valueobjects.NotificationPriority;
import com.netcore.cleanwave.platform.notifications.domain.model.valueobjects.NotificationType;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Aggregate root for the Notifications bounded context.
 *
 * <p>Represents a notification dispatched to a user, storing its category, urgency,
 * read state, and an optional redirection link.</p>
 */
@NullMarked
public class Notification extends AbstractDomainAggregateRoot<Notification> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private String title;

    @Getter
    private String message;

    @Getter
    private NotificationType type;

    @Getter
    private NotificationPriority priority;

    @Getter
    private boolean isRead;

    @Getter
    @Nullable
    private String link;

    /**
     * Default constructor. Initialises the read state to {@code false}.
     */
    public Notification() {
        this.isRead = false;
    }

    /**
     * Creates a notification with individual primitive attributes.
     * Read state is initialised to {@code false}.
     *
     * @param userId   the unique identifier of the target user
     * @param title    the title header of the notification
     * @param message  the main description body text
     * @param type     the category classification of the notification
     * @param priority the priority/urgency level
     * @param link     an optional redirection URI (can be null)
     */
    public Notification(Long userId, String title, String message, NotificationType type,
                        NotificationPriority priority, @Nullable String link) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = priority;
        this.link = link;
        this.isRead = false;
    }

    /**
     * Creates a notification from a {@link CreateNotificationCommand}.
     *
     * @param command the command containing creation data
     */
    public Notification(CreateNotificationCommand command) {
        this.userId = command.userId();
        this.title = command.title();
        this.message = command.message();
        this.type = NotificationType.valueOf(command.type().toUpperCase());
        this.priority = NotificationPriority.valueOf(command.priority().toUpperCase());
        this.link = command.link();
        this.isRead = false;
    }

    /**
     * Marks this notification as read.
     *
     * @return this aggregate instance (fluent API)
     */
    public Notification markAsRead() {
        this.isRead = true;
        return this;
    }
}

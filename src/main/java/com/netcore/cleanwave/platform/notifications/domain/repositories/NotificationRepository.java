package com.netcore.cleanwave.platform.notifications.domain.repositories;

import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for managing {@link Notification} aggregates.
 */
public interface NotificationRepository {
    /**
     * Persists the given notification aggregate.
     *
     * @param notification the notification to save
     * @return the saved notification aggregate
     */
    Notification save(Notification notification);

    /**
     * Retrieves a notification by its unique identifier.
     *
     * @param id the unique identifier of the notification
     * @return an {@link Optional} containing the notification if found, empty otherwise
     */
    Optional<Notification> findById(Long id);

    /**
     * Retrieves all notifications registered for a given user.
     *
     * @param userId the user identifier
     * @return the list of notifications matching the user ID
     */
    List<Notification> findByUserId(Long userId);

    /**
     * Deletes a notification by its unique identifier.
     *
     * @param id the unique identifier of the notification to delete
     */
    void deleteById(Long id);
}

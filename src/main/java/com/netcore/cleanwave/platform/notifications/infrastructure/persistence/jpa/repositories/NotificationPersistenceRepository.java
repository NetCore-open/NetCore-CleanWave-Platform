package com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.repositories;

import com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link NotificationPersistenceEntity} entity.
 */
@Repository
public interface NotificationPersistenceRepository extends JpaRepository<NotificationPersistenceEntity, Long> {
    /**
     * Finds all notification entities belonging to a specific user.
     *
     * @param userId the user identifier
     * @return the list of notifications
     */
    List<NotificationPersistenceEntity> findByUserId(Long userId);
}

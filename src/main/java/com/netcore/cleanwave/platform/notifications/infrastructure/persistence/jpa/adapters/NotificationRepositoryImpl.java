package com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;
import com.netcore.cleanwave.platform.notifications.domain.repositories.NotificationRepository;
import com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.assemblers.NotificationPersistenceAssembler;
import com.netcore.cleanwave.platform.notifications.infrastructure.persistence.jpa.repositories.NotificationPersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter implementing {@link NotificationRepository} using Spring Data JPA.
 */
@NullMarked
@Component
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationPersistenceRepository notificationPersistenceRepository;

    public NotificationRepositoryImpl(NotificationPersistenceRepository notificationPersistenceRepository) {
        this.notificationPersistenceRepository = notificationPersistenceRepository;
    }

    @Override
    public Notification save(Notification notification) {
        var entity = NotificationPersistenceAssembler.toPersistenceFromDomain(notification);
        var savedEntity = notificationPersistenceRepository.save(entity);
        return NotificationPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationPersistenceRepository.findById(id)
                .map(NotificationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        return notificationPersistenceRepository.findByUserId(userId).stream()
                .map(NotificationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        notificationPersistenceRepository.deleteById(id);
    }
}

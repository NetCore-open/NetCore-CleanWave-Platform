package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;
import com.netcore.cleanwave.platform.billing.domain.repositories.SubscriptionRepository;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.assemblers.SubscriptionPersistenceAssembler;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.repositories.SubscriptionPersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter implementing {@link SubscriptionRepository} using Spring Data JPA.
 */
@NullMarked
@Component
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionPersistenceRepository subscriptionPersistenceRepository;

    public SubscriptionRepositoryImpl(SubscriptionPersistenceRepository subscriptionPersistenceRepository) {
        this.subscriptionPersistenceRepository = subscriptionPersistenceRepository;
    }

    @Override
    public Subscription save(Subscription subscription) {
        var entity = SubscriptionPersistenceAssembler.toPersistenceFromDomain(subscription);
        var savedEntity = subscriptionPersistenceRepository.save(entity);
        return SubscriptionPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionPersistenceRepository.findById(id)
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Subscription> findByLaundryId(Long laundryId) {
        return subscriptionPersistenceRepository.findByLaundryId(laundryId).stream()
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}

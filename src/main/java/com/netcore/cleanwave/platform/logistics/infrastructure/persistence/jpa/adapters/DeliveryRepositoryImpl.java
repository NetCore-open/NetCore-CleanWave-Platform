package com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;
import com.netcore.cleanwave.platform.logistics.domain.repositories.DeliveryRepository;
import com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.assemblers.DeliveryPersistenceAssembler;
import com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.repositories.DeliveryPersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter implementing {@link DeliveryRepository} using Spring Data JPA.
 */
@NullMarked
@Component
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryPersistenceRepository deliveryPersistenceRepository;

    public DeliveryRepositoryImpl(DeliveryPersistenceRepository deliveryPersistenceRepository) {
        this.deliveryPersistenceRepository = deliveryPersistenceRepository;
    }

    @Override
    public Delivery save(Delivery delivery) {
        var entity = DeliveryPersistenceAssembler.toPersistenceFromDomain(delivery);
        var savedEntity = deliveryPersistenceRepository.save(entity);
        return DeliveryPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Delivery> findById(Long id) {
        return deliveryPersistenceRepository.findById(id)
                .map(DeliveryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryPersistenceRepository.findAll().stream()
                .map(DeliveryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Delivery> findByUserId(Long userId) {
        return deliveryPersistenceRepository.findByUserId(userId).stream()
                .map(DeliveryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        deliveryPersistenceRepository.deleteById(id);
    }
}

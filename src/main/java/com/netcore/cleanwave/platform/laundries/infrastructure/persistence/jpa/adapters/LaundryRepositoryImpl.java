package com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.repositories.LaundryRepository;
import com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.assemblers.LaundryPersistenceAssembler;
import com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.repositories.LaundryPersistenceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LaundryRepositoryImpl implements LaundryRepository {
    private final LaundryPersistenceRepository laundryPersistenceRepository;

    public LaundryRepositoryImpl(LaundryPersistenceRepository laundryPersistenceRepository) {
        this.laundryPersistenceRepository = laundryPersistenceRepository;
    }

    @Override
    public Laundry save(Laundry laundry) {
        var entity = LaundryPersistenceAssembler.toPersistenceFromDomain(laundry);
        var savedEntity = laundryPersistenceRepository.save(entity);
        return LaundryPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Laundry> findById(Long id) {
        return laundryPersistenceRepository.findById(id)
                .map(LaundryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Laundry> findAll() {
        return laundryPersistenceRepository.findAll().stream()
                .map(LaundryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return laundryPersistenceRepository.existsByName(name);
    }
}

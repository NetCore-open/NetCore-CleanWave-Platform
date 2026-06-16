package com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.repositories.LaundryRepository;
import com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.assemblers.LaundryPersistenceAssembler;
import com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.repositories.LaundryPersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the domain's {@link LaundryRepository}
 * contract using JPA.
 *
 * <p>Bridges the domain layer and the JPA persistence layer by converting
 * between {@link Laundry} domain aggregates and {@code LaundryPersistenceEntity}
 * objects via {@link LaundryPersistenceAssembler}.</p>
 */
@NullMarked
@Component
public class LaundryRepositoryImpl implements LaundryRepository {
    private final LaundryPersistenceRepository laundryPersistenceRepository;

    public LaundryRepositoryImpl(LaundryPersistenceRepository laundryPersistenceRepository) {
        this.laundryPersistenceRepository = laundryPersistenceRepository;
    }

    /**
     * Persists the given laundry aggregate, creating or updating as needed.
     *
     * @param laundry the laundry aggregate to persist
     * @return the persisted laundry aggregate with its assigned identity
     */
    @Override
    public Laundry save(Laundry laundry) {
        var entity = LaundryPersistenceAssembler.toPersistenceFromDomain(laundry);
        var savedEntity = laundryPersistenceRepository.save(entity);
        return LaundryPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    /**
     * Retrieves a laundry by its persistence identity.
     *
     * @param id the persistence identity to look up
     * @return an {@link Optional} containing the laundry if found, empty otherwise
     */
    @Override
    public Optional<Laundry> findById(Long id) {
        return laundryPersistenceRepository.findById(id)
                .map(LaundryPersistenceAssembler::toDomainFromPersistence);
    }

    /**
     * Retrieves all registered laundries.
     *
     * @return the list of all laundry aggregates
     */
    @Override
    public List<Laundry> findAll() {
        return laundryPersistenceRepository.findAll().stream()
                .map(LaundryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    /**
     * Checks whether a laundry with the given name already exists.
     *
     * @param name the name to check
     * @return {@code true} if a laundry with that name exists, {@code false} otherwise
     */
    @Override
    public boolean existsByName(String name) {
        return laundryPersistenceRepository.existsByName(name);
    }
}

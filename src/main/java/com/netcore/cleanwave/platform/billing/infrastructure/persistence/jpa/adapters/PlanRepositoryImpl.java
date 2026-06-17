package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan;
import com.netcore.cleanwave.platform.billing.domain.repositories.PlanRepository;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.assemblers.PlanPersistenceAssembler;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.repositories.PlanPersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter implementing {@link PlanRepository} using Spring Data JPA.
 */
@NullMarked
@Component
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanPersistenceRepository planPersistenceRepository;

    public PlanRepositoryImpl(PlanPersistenceRepository planPersistenceRepository) {
        this.planPersistenceRepository = planPersistenceRepository;
    }

    @Override
    public Plan save(Plan plan) {
        var entity = PlanPersistenceAssembler.toPersistenceFromDomain(plan);
        var savedEntity = planPersistenceRepository.save(entity);
        return PlanPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return planPersistenceRepository.findById(id)
                .map(PlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Plan> findAll() {
        return planPersistenceRepository.findAll().stream()
                .map(PlanPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return planPersistenceRepository.existsByName(name);
    }
}

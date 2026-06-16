package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities.PlanPersistenceEntity;

/**
 * Assembler that converts between {@link Plan} domain aggregates and
 * {@link PlanPersistenceEntity} JPA persistence entities.
 */
public class PlanPersistenceAssembler {

    /**
     * Converts a {@link PlanPersistenceEntity} to a {@link Plan} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static Plan toDomainFromPersistence(PlanPersistenceEntity entity) {
        var domain = new Plan(
                entity.getName(),
                entity.getPrice(),
                entity.getType(),
                entity.getBillingPeriod(),
                entity.getLaundryFeatures(),
                entity.getClientFeatures(),
                entity.isRecommended()
        );
        domain.setId(entity.getId());
        return domain;
    }

    /**
     * Converts a {@link Plan} domain aggregate to a {@link PlanPersistenceEntity}.
     *
     * @param domain the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static PlanPersistenceEntity toPersistenceFromDomain(Plan domain) {
        var entity = new PlanPersistenceEntity(
                domain.getName(),
                domain.getPrice(),
                domain.getType(),
                domain.getBillingPeriod(),
                domain.getLaundryFeatures(),
                domain.getClientFeatures(),
                domain.isRecommended()
        );
        entity.setId(domain.getId());
        return entity;
    }
}

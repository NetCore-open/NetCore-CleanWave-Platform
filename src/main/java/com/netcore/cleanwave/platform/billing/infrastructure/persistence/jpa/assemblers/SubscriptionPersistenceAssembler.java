package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;

/**
 * Assembler that converts between {@link Subscription} domain aggregates and
 * {@link SubscriptionPersistenceEntity} JPA persistence entities.
 */
public class SubscriptionPersistenceAssembler {

    /**
     * Converts a {@link SubscriptionPersistenceEntity} to a {@link Subscription} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static Subscription toDomainFromPersistence(SubscriptionPersistenceEntity entity) {
        var domain = new Subscription(
                entity.getPlanId(),
                entity.getLaundryId(),
                entity.getStartDate(),
                entity.getEndDate()
        );
        domain.setId(entity.getId());
        if (entity.getStatus() == com.netcore.cleanwave.platform.billing.domain.model.valueobjects.SubscriptionStatus.CANCELLED) {
            domain.cancel();
        }
        return domain;
    }

    /**
     * Converts a {@link Subscription} domain aggregate to a {@link SubscriptionPersistenceEntity}.
     *
     * @param domain the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static SubscriptionPersistenceEntity toPersistenceFromDomain(Subscription domain) {
        var entity = new SubscriptionPersistenceEntity(
                domain.getPlanId(),
                domain.getLaundryId(),
                domain.getStatus(),
                domain.getStartDate(),
                domain.getEndDate()
        );
        entity.setId(domain.getId());
        return entity;
    }
}

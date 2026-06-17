package com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.entities.LaundryPersistenceEntity;

/**
 * Assembler that converts between {@link Laundry} domain aggregates and
 * {@link LaundryPersistenceEntity} JPA persistence entities.
 *
 * <p>Keeps the translation logic in one place, preventing domain and
 * infrastructure concerns from leaking into each other.</p>
 */
public class LaundryPersistenceAssembler {

    /**
     * Converts a {@link LaundryPersistenceEntity} to a {@link Laundry} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static Laundry toDomainFromPersistence(LaundryPersistenceEntity entity) {
        var domain = new Laundry(entity.getName(), entity.getAddress(), entity.getRating(), entity.getImageUrl());
        domain.setId(entity.getId());
        domain.updateStatus(entity.getStatus());
        return domain;
    }

    /**
     * Converts a {@link Laundry} domain aggregate to a {@link LaundryPersistenceEntity}.
     *
     * @param domain the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static LaundryPersistenceEntity toPersistenceFromDomain(Laundry domain) {
        var entity = new LaundryPersistenceEntity(domain.getName(), domain.getAddress(), domain.getRating(),
                domain.getImageUrl(), domain.getStatus());
        entity.setId(domain.getId());
        return entity;
    }
}

package com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;
import com.netcore.cleanwave.platform.logistics.infrastructure.persistence.jpa.entities.DeliveryPersistenceEntity;

/**
 * Assembler that converts between {@link Delivery} domain aggregates and
 * {@link DeliveryPersistenceEntity} JPA persistence entities.
 */
public class DeliveryPersistenceAssembler {

    /**
     * Converts a {@link DeliveryPersistenceEntity} to a {@link Delivery} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static Delivery toDomainFromPersistence(DeliveryPersistenceEntity entity) {
        var domain = new Delivery(
                entity.getOrderId(),
                entity.getUserId(),
                entity.getType(),
                entity.getAddress(),
                entity.getScheduledDate(),
                entity.getDriverName(),
                entity.getDriverPhone(),
                entity.getNotes()
        );
        domain.setId(entity.getId());
        domain.updateStatus(entity.getStatus(), entity.getDriverName(), entity.getDriverPhone());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    /**
     * Converts a {@link Delivery} domain aggregate to a {@link DeliveryPersistenceEntity}.
     *
     * @param domain the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static DeliveryPersistenceEntity toPersistenceFromDomain(Delivery domain) {
        var entity = new DeliveryPersistenceEntity(
                domain.getOrderId(),
                domain.getUserId(),
                domain.getType(),
                domain.getStatus(),
                domain.getAddress(),
                domain.getScheduledDate(),
                domain.getDriverName(),
                domain.getDriverPhone(),
                domain.getNotes()
        );
        entity.setId(domain.getId());
        return entity;
    }
}

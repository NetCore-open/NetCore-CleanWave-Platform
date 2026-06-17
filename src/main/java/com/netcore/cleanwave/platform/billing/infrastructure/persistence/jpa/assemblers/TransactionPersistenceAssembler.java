package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.entities.TransactionPersistenceEntity;

/**
 * Assembler that converts between {@link Transaction} domain aggregates and
 * {@link TransactionPersistenceEntity} JPA persistence entities.
 */
public class TransactionPersistenceAssembler {

    /**
     * Converts a {@link TransactionPersistenceEntity} to a {@link Transaction} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static Transaction toDomainFromPersistence(TransactionPersistenceEntity entity) {
        var domain = new Transaction(
                entity.getSubscriptionId(),
                entity.getAmount(),
                entity.getDate(),
                entity.getStatus()
        );
        domain.setId(entity.getId());
        return domain;
    }

    /**
     * Converts a {@link Transaction} domain aggregate to a {@link TransactionPersistenceEntity}.
     *
     * @param domain the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static TransactionPersistenceEntity toPersistenceFromDomain(Transaction domain) {
        var entity = new TransactionPersistenceEntity(
                domain.getSubscriptionId(),
                domain.getAmount(),
                domain.getDate(),
                domain.getStatus()
        );
        entity.setId(domain.getId());
        return entity;
    }
}

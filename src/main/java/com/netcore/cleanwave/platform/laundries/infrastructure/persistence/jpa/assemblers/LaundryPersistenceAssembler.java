package com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.entities.LaundryPersistenceEntity;

public class LaundryPersistenceAssembler {
    public static Laundry toDomainFromPersistence(LaundryPersistenceEntity entity) {
        var domain = new Laundry(entity.getName(), entity.getAddress(), entity.getRating(), entity.getImageUrl());
        domain.setId(entity.getId());
        domain.updateStatus(entity.getStatus());
        return domain;
    }

    public static LaundryPersistenceEntity toPersistenceFromDomain(Laundry domain) {
        var entity = new LaundryPersistenceEntity(domain.getName(), domain.getAddress(), domain.getRating(),
                domain.getImageUrl(), domain.getStatus());
        entity.setId(domain.getId());
        return entity;
    }
}

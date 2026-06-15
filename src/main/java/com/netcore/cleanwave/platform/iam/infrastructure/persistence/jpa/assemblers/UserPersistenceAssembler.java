package com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

public class UserPersistenceAssembler {
    public static User toDomainFromPersistence(UserPersistenceEntity entity) {
        var user = new User(entity.getUsername(), entity.getPassword(), entity.getRoles());
        user.setId(entity.getId());
        return user;
    }

    public static UserPersistenceEntity toPersistenceFromDomain(User domain) {
        var entity = new UserPersistenceEntity(domain.getUsername(), domain.getPassword(), domain.getRoles());
        entity.setId(domain.getId());
        return entity;
    }
}

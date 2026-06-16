package com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

/**
 * Assembler that converts between {@link User} domain aggregates and
 * {@link UserPersistenceEntity} JPA persistence entities.
 *
 * <p>Keeps the translation logic in one place, preventing domain and
 * infrastructure concerns from leaking into each other.</p>
 */
public class UserPersistenceAssembler {

    /**
     * Converts a {@link UserPersistenceEntity} to a {@link User} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static User toDomainFromPersistence(UserPersistenceEntity entity) {
        var user = new User(entity.getUsername(), entity.getPassword(), entity.getRoles());
        user.setId(entity.getId());
        return user;
    }

    /**
     * Converts a {@link User} domain aggregate to a {@link UserPersistenceEntity}.
     *
     * @param domain the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static UserPersistenceEntity toPersistenceFromDomain(User domain) {
        var entity = new UserPersistenceEntity(domain.getUsername(), domain.getPassword(), domain.getRoles());
        entity.setId(domain.getId());
        return entity;
    }
}

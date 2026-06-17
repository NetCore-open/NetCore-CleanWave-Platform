package com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.domain.repositories.UserRepository;
import com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Infrastructure adapter that implements the domain's {@link UserRepository}
 * contract using JPA.
 *
 * <p>Bridges the domain layer and the JPA persistence layer by converting
 * between {@link User} domain aggregates and {@code UserPersistenceEntity}
 * objects via {@link UserPersistenceAssembler}.</p>
 */
@NullMarked
@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserPersistenceRepository userPersistenceRepository;

    public UserRepositoryImpl(UserPersistenceRepository userPersistenceRepository) {
        this.userPersistenceRepository = userPersistenceRepository;
    }

    /**
     * Persists the given user aggregate, creating or updating as needed.
     *
     * @param user the user aggregate to persist
     * @return the persisted user aggregate with its assigned identity
     */
    @Override
    public User save(User user) {
        var entity = UserPersistenceAssembler.toPersistenceFromDomain(user);
        var savedEntity = userPersistenceRepository.save(entity);
        return UserPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    /**
     * Retrieves a user by its persistence identity.
     *
     * @param id the persistence identity to look up
     * @return an {@link Optional} containing the user if found, empty otherwise
     */
    @Override
    public Optional<User> findById(Long id) {
        return userPersistenceRepository.findById(id)
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    /**
     * Retrieves a user by their unique username.
     *
     * @param username the username to look up
     * @return an {@link Optional} containing the user if found, empty otherwise
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userPersistenceRepository.findByUsername(username)
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    /**
     * Checks whether a user with the given username already exists.
     *
     * @param username the username to check
     * @return {@code true} if a user with that username exists, {@code false} otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return userPersistenceRepository.existsByUsername(username);
    }
}

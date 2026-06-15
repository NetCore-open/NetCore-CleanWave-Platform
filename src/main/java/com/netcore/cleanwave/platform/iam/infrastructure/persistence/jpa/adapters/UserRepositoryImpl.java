package com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.domain.repositories.UserRepository;
import com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserPersistenceRepository userPersistenceRepository;

    public UserRepositoryImpl(UserPersistenceRepository userPersistenceRepository) {
        this.userPersistenceRepository = userPersistenceRepository;
    }

    @Override
    public User save(User user) {
        var entity = UserPersistenceAssembler.toPersistenceFromDomain(user);
        var savedEntity = userPersistenceRepository.save(entity);
        return UserPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userPersistenceRepository.findById(id)
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userPersistenceRepository.findByUsername(username)
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userPersistenceRepository.existsByUsername(username);
    }
}

package com.netcore.cleanwave.platform.iam.domain.repositories;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}

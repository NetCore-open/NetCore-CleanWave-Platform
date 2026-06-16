package com.netcore.cleanwave.platform.iam.application.internal.queryservices;

import com.netcore.cleanwave.platform.iam.application.queryservices.UserQueryService;
import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.netcore.cleanwave.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import com.netcore.cleanwave.platform.iam.domain.repositories.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service implementation that handles user read operations.
 *
 * <p>Implements {@link UserQueryService} and delegates all queries to the
 * {@link UserRepository}, keeping query orchestration logic separate from
 * command (write) operations.</p>
 */
@NullMarked
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by its persistence identity.
     *
     * @param query the query carrying the user id
     * @return an {@link Optional} containing the user if found, empty otherwise
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    /**
     * Retrieves a user by their unique username.
     *
     * @param query the query carrying the username
     * @return an {@link Optional} containing the user if found, empty otherwise
     */
    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}

package com.netcore.cleanwave.platform.iam.application.internal.queryservices;

import com.netcore.cleanwave.platform.iam.application.queryservices.UserQueryService;
import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.netcore.cleanwave.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import com.netcore.cleanwave.platform.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}

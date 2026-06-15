package com.netcore.cleanwave.platform.iam.application.internal.commandservices;

import com.netcore.cleanwave.platform.iam.application.commandservices.UserCommandService;
import com.netcore.cleanwave.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.netcore.cleanwave.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.domain.model.commands.SignInCommand;
import com.netcore.cleanwave.platform.iam.domain.model.commands.SignUpCommand;
import com.netcore.cleanwave.platform.iam.domain.repositories.UserRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Result<User, ApplicationError> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            return Result.failure(ApplicationError.conflict("User", "Username '" + command.username() + "' already exists"));
        }
        var hashedPassword = hashingService.encode(command.password());
        var user = new User(command.username(), hashedPassword, new HashSet<>(command.roles()));
        var savedUser = userRepository.save(user);
        return Result.success(savedUser);
    }

    @Override
    public Result<UserAndToken, ApplicationError> handle(SignInCommand command) {
        var userOpt = userRepository.findByUsername(command.username());
        if (userOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", "User not found"));
        }
        var user = userOpt.get();
        if (!hashingService.matches(command.password(), user.getPassword())) {
            return Result.failure(ApplicationError.validationError("Credentials", "Invalid password"));
        }
        var token = tokenService.generateToken(user.getUsername());
        return Result.success(new UserAndToken(user, token));
    }
}

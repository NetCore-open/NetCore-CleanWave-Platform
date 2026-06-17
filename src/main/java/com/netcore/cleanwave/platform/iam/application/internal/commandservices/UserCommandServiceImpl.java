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
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Application service implementation that handles user authentication write operations.
 *
 * <p>Implements {@link UserCommandService} and orchestrates user registration
 * ({@link SignUpCommand}) and authentication ({@link SignInCommand}).
 * Password hashing is delegated to the {@link HashingService} and token
 * generation to the {@link TokenService}, keeping those concerns out of
 * the domain model.</p>
 */
@NullMarked
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

    /**
     * Handles the {@link SignUpCommand} to register a new user.
     *
     * <p>Rejects registration with a conflict error if a user with the same
     * username already exists. The password is hashed before persistence.</p>
     *
     * @param command the sign-up command carrying the username, password and roles
     * @return {@code Result.success} with the persisted {@link User},
     *         or {@code Result.failure} with the relevant {@link ApplicationError}
     */
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

    /**
     * Handles the {@link SignInCommand} to authenticate an existing user.
     *
     * <p>Returns a failure result if the user is not found or the password does
     * not match. On success, generates and returns a JWT token alongside the
     * user aggregate.</p>
     *
     * @param command the sign-in command carrying the username and plain-text password
     * @return {@code Result.success} with a {@link UserAndToken} pair,
     *         or {@code Result.failure} with the relevant {@link ApplicationError}
     */
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

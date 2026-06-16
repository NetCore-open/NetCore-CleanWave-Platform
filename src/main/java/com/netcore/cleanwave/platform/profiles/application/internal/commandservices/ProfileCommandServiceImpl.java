package com.netcore.cleanwave.platform.profiles.application.internal.commandservices;

import com.netcore.cleanwave.platform.profiles.application.commandservices.ProfileCommandService;
import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.profiles.domain.repositories.ProfileRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that handles profile write operations.
 *
 * <p>Implements {@link ProfileCommandService} and orchestrates the creation
 * of {@link Profile} aggregates. Enforces the uniqueness invariant on
 * email address before delegating persistence to the {@link ProfileRepository}.</p>
 */
@NullMarked
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;


    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    /**
     * Handles the {@link CreateProfileCommand} to create and persist a new profile.
     *
     * <p>Rejects the command with a conflict error if a profile with the same
     * email address already exists. Validation errors thrown by value-object
     * constructors are caught and returned as {@code VALIDATION_ERROR} results.</p>
     *
     * @param command the create-profile command
     * @return {@code Result.success} with the persisted profile,
     *         or {@code Result.failure} with the relevant {@link ApplicationError}
     */
    @Override
    public Result<Profile, ApplicationError> handle(CreateProfileCommand command) {
        try {
            var emailAddress = new EmailAddress(command.email());
            if (profileRepository.existsByEmailAddress(emailAddress)) {
                return Result.failure(ApplicationError.conflict("Profile",
                        "A profile with email address '%s' already exists".formatted(command.email())));
            }
            var profile = new Profile(command);
            var savedProfile = profileRepository.save(profile);
            return Result.success(savedProfile);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Profile", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Profile creation", e.getMessage()));
        }
    }
}

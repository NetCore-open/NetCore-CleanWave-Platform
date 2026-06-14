package com.netcore.cleanwave.platform.profiles.application.internal.commandservices;

import com.netcore.cleanwave.platform.profiles.application.commandservices.ProfileCommandService;
import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.profiles.domain.repositories.ProfileRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;


    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


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


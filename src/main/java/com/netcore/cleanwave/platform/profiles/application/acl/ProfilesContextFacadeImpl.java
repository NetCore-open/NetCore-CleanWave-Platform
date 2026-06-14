package com.netcore.cleanwave.platform.profiles.application.acl;

import com.netcore.cleanwave.platform.profiles.application.commandservices.ProfileCommandService;
import com.netcore.cleanwave.platform.profiles.application.queryservices.ProfileQueryService;
import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Profiles ACL facade.
 *
 * <p>Provides a simplified integration surface for other bounded contexts that need profile
 * operations without coupling to Profiles internal models.</p>
 */
@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;


    public ProfilesContextFacadeImpl(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }


    /**
     * Creates a profile and returns its identifier
     *
     * @return created profile identifier or {@code 0L} when creation fails.
     */
    @Override
    public Long createProfile(String firstName, String lastname, String email,
                              String street, String number, String city, String postalCode, String country) {
        var createProfileCommand = new CreateProfileCommand(firstName, lastname, email,
                street, number, city, postalCode, country);
        var result = profileCommandService.handle(createProfileCommand);
        return result.toOptional()
                .map(profile -> profile.getId())
                .orElse(0L);
    }

    /**
     * Fetches a profile identifier by email.
     *
     * @param email profile email address
     * @return profile identifier, or {@code 0L} when not found
     */
    @Override
    public Long fetchProfileIdByEmail(String email) {
        var getProfileByEmailQuery = new GetProfileByEmailQuery(new EmailAddress(email));
        var profile = profileQueryService.handle(getProfileByEmailQuery);
        return profile.isEmpty() ? Long.valueOf(0L) : profile.get().getId();
    }
}


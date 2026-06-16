package com.netcore.cleanwave.platform.profiles.application.internal.queryservices;

import com.netcore.cleanwave.platform.profiles.application.queryservices.ProfileQueryService;
import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.netcore.cleanwave.platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.netcore.cleanwave.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.netcore.cleanwave.platform.profiles.domain.repositories.ProfileRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementation that handles profile read operations.
 *
 * <p>Implements {@link ProfileQueryService} and delegates all queries to the
 * {@link ProfileRepository}, keeping query orchestration logic separate from
 * command (write) operations.</p>
 */
@NullMarked
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;


    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Retrieves a profile by its persistence identity.
     *
     * @param query the query carrying the profile id
     * @return an {@link Optional} containing the profile if found, empty otherwise
     */
    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    /**
     * Retrieves a profile by its email address.
     *
     * @param query the query carrying the email address value object
     * @return an {@link Optional} containing the profile if found, empty otherwise
     */
    @Override
    public Optional<Profile> handle(GetProfileByEmailQuery query) {
        return profileRepository.findByEmailAddress(query.emailAddress());
    }

    /**
     * Retrieves all profiles.
     *
     * @param query the query (no parameters)
     * @return the list of all profile aggregates
     */
    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }
}

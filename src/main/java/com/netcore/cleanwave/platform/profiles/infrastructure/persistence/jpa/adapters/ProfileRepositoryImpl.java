package com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.profiles.domain.repositories.ProfileRepository;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.assemblers.ProfilePersistenceAssembler;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.repositories.ProfilePersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter that implements the domain's {@link ProfileRepository}
 * contract using JPA.
 *
 * <p>Bridges the domain layer and the JPA persistence layer by converting
 * between {@link Profile} domain aggregates and {@code ProfilePersistenceEntity}
 * objects via {@link ProfilePersistenceAssembler}.</p>
 *
 * <p>After persisting a new profile, this adapter calls {@link Profile#onCreated()}
 * and publishes all registered domain events through the
 * {@link ApplicationEventPublisher} so other bounded contexts can react.</p>
 */
@NullMarked
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfilePersistenceRepository profilePersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;


    public ProfileRepositoryImpl(ProfilePersistenceRepository profilePersistenceRepository, ApplicationEventPublisher eventPublisher) {
        this.profilePersistenceRepository = profilePersistenceRepository;
        this.eventPublisher = eventPublisher;
    }


    /**
     * Retrieves a profile by its persistence identity.
     *
     * @param id the persistence identity to look up
     * @return an {@link Optional} containing the profile if found, empty otherwise
     */
    @Override
    public Optional<Profile> findById(Long id) {
        return profilePersistenceRepository.findById(id).map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    /**
     * Retrieves a profile by its email address.
     *
     * @param emailAddress the email address value object to look up
     * @return an {@link Optional} containing the profile if found, empty otherwise
     */
    @Override
    public Optional<Profile> findByEmailAddress(EmailAddress emailAddress) {
        return profilePersistenceRepository.findByEmailAddress(emailAddress).map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    /**
     * Retrieves all profiles.
     *
     * @return the list of all profile aggregates
     */
    @Override
    public List<Profile> findAll() {
        return profilePersistenceRepository.findAll().stream().map(ProfilePersistenceAssembler::toDomainFromPersistence).toList();
    }

    /**
     * Persists the given profile aggregate, creating or updating as needed.
     *
     * <p>For newly created profiles, triggers {@link Profile#onCreated()} after
     * the JPA identity is assigned, then publishes and clears all registered
     * domain events.</p>
     *
     * @param profile the profile aggregate to persist
     * @return the persisted profile aggregate with its assigned identity
     */
    @Override
    public Profile save(Profile profile) {
        boolean isNew = profile.getId() == null;
        var savedEntity = profilePersistenceRepository.save(ProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        var savedProfile = ProfilePersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedProfile.onCreated();
            savedProfile.domainEvents().forEach(eventPublisher::publishEvent);
            savedProfile.clearDomainEvents();
        }
        return savedProfile;
    }

    /**
     * Checks whether a profile with the given email address already exists.
     *
     * @param emailAddress the email address value object to check
     * @return {@code true} if a profile with that email exists, {@code false} otherwise
     */
    @Override
    public boolean existsByEmailAddress(EmailAddress emailAddress) {
        return profilePersistenceRepository.countByEmailAddress(emailAddress) > 0;
    }
}

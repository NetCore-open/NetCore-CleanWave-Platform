package com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.PersonName;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.StreetAddress;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.embeddables.StreetAddressPersistenceEmbeddable;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;

/**
 * Assembler that converts between {@link Profile} domain aggregates and
 * {@link ProfilePersistenceEntity} JPA persistence entities.
 *
 * <p>Keeps the translation logic in one place, preventing domain and
 * infrastructure concerns from leaking into each other.</p>
 */
public final class ProfilePersistenceAssembler {

    private ProfilePersistenceAssembler() {
    }

    /**
     * Converts a {@link ProfilePersistenceEntity} to a {@link Profile} domain aggregate.
     *
     * @param entity the JPA persistence entity to convert
     * @return the corresponding domain aggregate
     */
    public static Profile toDomainFromPersistence(ProfilePersistenceEntity entity) {
        return new Profile(
                entity.getId(),
                toDomainFromPersistence(entity.getName()),
                entity.getEmailAddress(),
                toDomainFromPersistence(entity.getStreetAddress()));
    }

    /**
     * Converts a {@link Profile} domain aggregate to a {@link ProfilePersistenceEntity}.
     *
     * @param profile the domain aggregate to convert
     * @return the corresponding JPA persistence entity
     */
    public static ProfilePersistenceEntity toPersistenceFromDomain(Profile profile) {
        var entity = new ProfilePersistenceEntity();
        entity.setId(profile.getId());
        entity.setName(toPersistenceFromDomain(profile.getName()));
        entity.setEmailAddress(profile.getEmailAddressValue());
        entity.setStreetAddress(toPersistenceFromDomain(profile.getStreetAddressValue()));
        return entity;
    }

    /**
     * Converts a {@link PersonNamePersistenceEmbeddable} to a {@link PersonName} value object.
     *
     * @param value the embeddable to convert; may be {@code null}
     * @return the corresponding value object, or {@code null} if {@code value} is {@code null}
     */
    public static PersonName toDomainFromPersistence(PersonNamePersistenceEmbeddable value) {
        return value == null ? null : new PersonName(value.getFirstName(), value.getLastName());
    }

    /**
     * Converts a {@link StreetAddressPersistenceEmbeddable} to a {@link StreetAddress} value object.
     *
     * @param value the embeddable to convert; may be {@code null}
     * @return the corresponding value object, or {@code null} if {@code value} is {@code null}
     */
    public static StreetAddress toDomainFromPersistence(StreetAddressPersistenceEmbeddable value) {
        return value == null ? null : new StreetAddress(value.getStreet(), value.getNumber(), value.getCity(),
                value.getPostalCode(), value.getCountry());
    }

    /**
     * Converts a {@link PersonName} value object to a {@link PersonNamePersistenceEmbeddable}.
     *
     * @param value the value object to convert; may be {@code null}
     * @return the corresponding embeddable, or {@code null} if {@code value} is {@code null}
     */
    public static PersonNamePersistenceEmbeddable toPersistenceFromDomain(PersonName value) {
        return value == null ? null : new PersonNamePersistenceEmbeddable(value.firstName(), value.lastName());
    }

    /**
     * Converts a {@link StreetAddress} value object to a {@link StreetAddressPersistenceEmbeddable}.
     *
     * @param value the value object to convert; may be {@code null}
     * @return the corresponding embeddable, or {@code null} if {@code value} is {@code null}
     */
    public static StreetAddressPersistenceEmbeddable toPersistenceFromDomain(StreetAddress value) {
        return value == null ? null : new StreetAddressPersistenceEmbeddable(value.street(), value.number(), value.city(),
                value.postalCode(), value.country());
    }

}

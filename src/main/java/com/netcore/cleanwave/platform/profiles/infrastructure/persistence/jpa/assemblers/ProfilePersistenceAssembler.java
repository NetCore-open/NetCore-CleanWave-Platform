package com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.assemblers;

import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.PersonName;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.StreetAddress;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.embeddables.StreetAddressPersistenceEmbeddable;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;

public final class ProfilePersistenceAssembler {
    private ProfilePersistenceAssembler() {
    }

    public static Profile toDomainFromPersistence(ProfilePersistenceEntity entity) {
        return new Profile(
                entity.getId(),
                toDomainFromPersistence(entity.getName()),
                entity.getEmailAddress(),
                toDomainFromPersistence(entity.getStreetAddress()));
    }

    public static ProfilePersistenceEntity toPersistenceFromDomain(Profile profile) {
        var entity = new ProfilePersistenceEntity();
        entity.setId(profile.getId());
        entity.setName(toPersistenceFromDomain(profile.getName()));
        entity.setEmailAddress(profile.getEmailAddressValue());
        entity.setStreetAddress(toPersistenceFromDomain(profile.getStreetAddressValue()));
        return entity;
    }

    public static PersonName toDomainFromPersistence(PersonNamePersistenceEmbeddable value) {
        return value == null ? null : new PersonName(value.getFirstName(), value.getLastName());
    }

    public static StreetAddress toDomainFromPersistence(StreetAddressPersistenceEmbeddable value) {
        return value == null ? null : new StreetAddress(value.getStreet(), value.getNumber(), value.getCity(),
                value.getPostalCode(), value.getCountry());
    }

    public static PersonNamePersistenceEmbeddable toPersistenceFromDomain(PersonName value) {
        return value == null ? null : new PersonNamePersistenceEmbeddable(value.firstName(), value.lastName());
    }

    public static StreetAddressPersistenceEmbeddable toPersistenceFromDomain(StreetAddress value) {
        return value == null ? null : new StreetAddressPersistenceEmbeddable(value.street(), value.number(), value.city(),
                value.postalCode(), value.country());
    }

}


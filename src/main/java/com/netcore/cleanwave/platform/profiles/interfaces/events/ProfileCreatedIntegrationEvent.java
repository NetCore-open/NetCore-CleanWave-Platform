package com.netcore.cleanwave.platform.profiles.interfaces.events;

import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;

/**
 * Integration event published by the {@code profiles} bounded context when a new
 * {@link Profile} has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code profiles} context.
 * Other bounded contexts (e.g. {@code learning}) must listen to this event rather
 * than to internal domain events such as
 * {@link com.netcore.cleanwave.platform.profiles.domain.model.events.ProfileCreatedEvent},
 * which is an internal concern of the {@code profiles} domain.</p>
 */
public record ProfileCreatedIntegrationEvent(
        Long profileId,
        String firstName,
        String lastName,
        String email,
        String street,
        String number,
        String city,
        String postalCode,
        String country
) {

    public static ProfileCreatedIntegrationEvent from(Profile profile) {
        var name = profile.getName();
        var address = profile.getStreetAddressValue();
        return  new ProfileCreatedIntegrationEvent(
                profile.getId(),
                name.firstName(),
                name.lastName(),
                profile.getEmailAddress(),
                address.street(),
                address.number(),
                address.city(),
                address.postalCode(),
                address.country()
        );
    }
}


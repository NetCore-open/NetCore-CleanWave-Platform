package com.netcore.cleanwave.platform.profiles.domain.model.events;

import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;

/**
 * Domain event published when a new {@link Profile} is successfully created and persisted.
 *
 * <p>Other bounded contexts (e.g. {@code learning}) can listen to this event
 * to react to profile creation without directly coupling to the {@code profiles}
 * application services.</p>
 */
public record ProfileCreatedEvent(Long profileId,
                                  String firstName, String lastName,
                                  String email,
                                  String street, String number, String city,String postalCode, String country) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link Profile}.
     *
     * @param profile the saved profile (must already carry a non-null id)
     * @return a fully populated {@link ProfileCreatedEvent}
     */
    public static ProfileCreatedEvent from(Profile profile){
        var name = profile.getName();
        var address = profile.getStreetAddressValue();
        return new ProfileCreatedEvent(
                profile.getId(),
                name.firstName(), name.lastName(),
                profile.getEmailAddress(),
                address.street(), address.number(), address.city(), address.postalCode(), address.country());
    }
}


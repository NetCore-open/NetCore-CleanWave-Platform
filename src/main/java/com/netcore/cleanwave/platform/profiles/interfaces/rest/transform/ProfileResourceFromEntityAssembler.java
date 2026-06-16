package com.netcore.cleanwave.platform.profiles.interfaces.rest.transform;

import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.interfaces.rest.resources.ProfileResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link Profile} domain aggregate into a
 * {@link ProfileResource} REST response resource.
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point.</p>
 */
@NullMarked
public class ProfileResourceFromEntityAssembler {

    /**
     * Converts the given {@link Profile} aggregate to a {@link ProfileResource}.
     *
     * @param entity the profile domain aggregate
     * @return the corresponding REST resource
     */
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getFullName(),
                entity.getEmailAddress(),
                entity.getStreetAddress()
        );
    }
}

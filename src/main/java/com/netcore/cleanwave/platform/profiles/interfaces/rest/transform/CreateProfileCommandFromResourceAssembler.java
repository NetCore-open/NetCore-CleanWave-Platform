package com.netcore.cleanwave.platform.profiles.interfaces.rest.transform;

import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link CreateProfileResource} REST request resource into
 * a {@link CreateProfileCommand} domain command.
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point for inbound profile data.</p>
 */
@NullMarked
public class CreateProfileCommandFromResourceAssembler {

    /**
     * Converts the given {@link CreateProfileResource} to a {@link CreateProfileCommand}.
     *
     * @param resource the REST request resource carrying the profile creation data
     * @return the corresponding domain command
     */
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource){
        return new CreateProfileCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.street(),
                resource.number(),
                resource.city(),
                resource.postalCode(),
                resource.country()
        );
    }
}

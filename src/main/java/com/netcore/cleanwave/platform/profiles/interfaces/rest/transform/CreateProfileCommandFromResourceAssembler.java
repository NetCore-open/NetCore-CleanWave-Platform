package com.netcore.cleanwave.platform.profiles.interfaces.rest.transform;

import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.profiles.interfaces.rest.resources.CreateProfileResource;

/**
 * Assembler to convert a CreateProfileResource to a CreateProfileCommand.
 */
public class CreateProfileCommandFromResourceAssembler {
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


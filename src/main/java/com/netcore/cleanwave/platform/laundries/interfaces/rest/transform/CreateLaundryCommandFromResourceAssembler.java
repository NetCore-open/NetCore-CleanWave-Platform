package com.netcore.cleanwave.platform.laundries.interfaces.rest.transform;

import com.netcore.cleanwave.platform.laundries.domain.model.commands.CreateLaundryCommand;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.resources.CreateLaundryResource;

public class CreateLaundryCommandFromResourceAssembler {
    public static CreateLaundryCommand toCommandFromResource(CreateLaundryResource resource) {
        return new CreateLaundryCommand(
                resource.name(),
                resource.address(),
                resource.rating(),
                resource.imageUrl()
        );
    }
}

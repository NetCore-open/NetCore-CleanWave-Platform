package com.netcore.cleanwave.platform.laundries.interfaces.rest.transform;

import com.netcore.cleanwave.platform.laundries.domain.model.commands.CreateLaundryCommand;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.resources.CreateLaundryResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link CreateLaundryResource} REST request resource into
 * a {@link CreateLaundryCommand} domain command.
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point for inbound laundry creation data.</p>
 */
@NullMarked
public class CreateLaundryCommandFromResourceAssembler {

    /**
     * Converts the given {@link CreateLaundryResource} to a {@link CreateLaundryCommand}.
     *
     * @param resource the REST request resource carrying the laundry creation data
     * @return the corresponding domain command
     */
    public static CreateLaundryCommand toCommandFromResource(CreateLaundryResource resource) {
        return new CreateLaundryCommand(
                resource.name(),
                resource.address(),
                resource.rating(),
                resource.imageUrl()
        );
    }
}

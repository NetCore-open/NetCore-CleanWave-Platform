package com.netcore.cleanwave.platform.billing.interfaces.rest.transform;

import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateSubscriptionCommand;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.CreateSubscriptionResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link CreateSubscriptionResource} REST request resource into
 * a {@link CreateSubscriptionCommand} domain command.
 */
@NullMarked
public class CreateSubscriptionCommandFromResourceAssembler {

    /**
     * Converts a {@link CreateSubscriptionResource} to a {@link CreateSubscriptionCommand}.
     *
     * @param resource the REST request resource containing creation parameters
     * @return the corresponding domain command
     */
    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        return new CreateSubscriptionCommand(
                resource.planId(),
                resource.laundryId()
        );
    }
}

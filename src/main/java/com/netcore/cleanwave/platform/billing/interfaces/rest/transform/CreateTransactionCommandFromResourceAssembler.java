package com.netcore.cleanwave.platform.billing.interfaces.rest.transform;

import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateTransactionCommand;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.CreateTransactionResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link CreateTransactionResource} REST request resource into
 * a {@link CreateTransactionCommand} domain command.
 */
@NullMarked
public class CreateTransactionCommandFromResourceAssembler {

    /**
     * Converts a {@link CreateTransactionResource} to a {@link CreateTransactionCommand}.
     *
     * @param resource the REST request resource containing creation parameters
     * @return the corresponding domain command
     */
    public static CreateTransactionCommand toCommandFromResource(CreateTransactionResource resource) {
        return new CreateTransactionCommand(
                resource.subscriptionId(),
                resource.amount()
        );
    }
}

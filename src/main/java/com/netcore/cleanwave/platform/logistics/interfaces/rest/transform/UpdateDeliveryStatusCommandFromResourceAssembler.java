package com.netcore.cleanwave.platform.logistics.interfaces.rest.transform;

import com.netcore.cleanwave.platform.logistics.domain.model.commands.UpdateDeliveryStatusCommand;
import com.netcore.cleanwave.platform.logistics.domain.model.valueobjects.DeliveryStatus;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.resources.UpdateDeliveryStatusResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link UpdateDeliveryStatusResource} REST request resource into
 * an {@link UpdateDeliveryStatusCommand} domain command.
 */
@NullMarked
public class UpdateDeliveryStatusCommandFromResourceAssembler {

    /**
     * Converts the given resource and ID parameter to an {@link UpdateDeliveryStatusCommand}.
     *
     * @param id       the delivery identifier from the path variable
     * @param resource the REST request resource carrying update parameters
     * @return the corresponding domain command
     */
    public static UpdateDeliveryStatusCommand toCommandFromResource(Long id, UpdateDeliveryStatusResource resource) {
        DeliveryStatus status = DeliveryStatus.valueOf(resource.status().toUpperCase());
        return new UpdateDeliveryStatusCommand(
                id,
                status,
                resource.driverName(),
                resource.driverPhone()
        );
    }
}

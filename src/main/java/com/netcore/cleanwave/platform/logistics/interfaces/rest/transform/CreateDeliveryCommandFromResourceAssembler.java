package com.netcore.cleanwave.platform.logistics.interfaces.rest.transform;

import com.netcore.cleanwave.platform.logistics.domain.model.commands.CreateDeliveryCommand;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.resources.CreateDeliveryResource;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Assembler that converts a {@link CreateDeliveryResource} REST request resource into
 * a {@link CreateDeliveryCommand} domain command.
 */
@NullMarked
public class CreateDeliveryCommandFromResourceAssembler {

    /**
     * Converts the given {@link CreateDeliveryResource} to a {@link CreateDeliveryCommand}.
     *
     * @param resource the REST request resource carrying delivery creation data
     * @return the corresponding domain command
     */
    public static CreateDeliveryCommand toCommandFromResource(CreateDeliveryResource resource) {
        // Handle input dates (either ISO datetime or ISO date)
        LocalDate date;
        if (resource.scheduledDate().contains("T")) {
            date = LocalDate.parse(resource.scheduledDate().split("T")[0]);
        } else {
            date = LocalDate.parse(resource.scheduledDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        }

        return new CreateDeliveryCommand(
                resource.orderId(),
                resource.userId(),
                resource.type(),
                resource.address(),
                date,
                resource.notes()
        );
    }
}

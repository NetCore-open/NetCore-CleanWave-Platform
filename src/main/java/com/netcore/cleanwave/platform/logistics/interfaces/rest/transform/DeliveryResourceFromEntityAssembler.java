package com.netcore.cleanwave.platform.logistics.interfaces.rest.transform;

import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.resources.DeliveryResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link Delivery} domain aggregate into a
 * {@link DeliveryResource} REST response resource.
 */
@NullMarked
public class DeliveryResourceFromEntityAssembler {

    /**
     * Converts a {@link Delivery} domain aggregate into a {@link DeliveryResource}.
     *
     * @param entity the delivery domain aggregate
     * @return the corresponding REST resource DTO
     */
    public static DeliveryResource toResourceFromEntity(Delivery entity) {
        String createdStr = entity.getCreatedAt() != null ? entity.getCreatedAt().toInstant().toString() : "";
        String updatedStr = entity.getUpdatedAt() != null ? entity.getUpdatedAt().toInstant().toString() : "";

        return new DeliveryResource(
                entity.getId(),
                entity.getOrderId(),
                entity.getUserId(),
                entity.getType().name(),
                entity.getStatus().name(),
                entity.getAddress(),
                entity.getScheduledDate().toString(),
                entity.getDriverName(),
                entity.getDriverPhone(),
                entity.getNotes(),
                createdStr,
                updatedStr
        );
    }
}

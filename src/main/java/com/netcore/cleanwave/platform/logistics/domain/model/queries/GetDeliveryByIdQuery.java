package com.netcore.cleanwave.platform.logistics.domain.model.queries;

/**
 * Query record to request delivery details by its identifier.
 *
 * @param id the unique identifier of the delivery
 */
public record GetDeliveryByIdQuery(
        Long id
) {
}

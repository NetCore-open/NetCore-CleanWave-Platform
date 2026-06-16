package com.netcore.cleanwave.platform.logistics.domain.model.queries;

/**
 * Query record to request all deliveries matching a specific user.
 *
 * @param userId the unique identifier of the customer
 */
public record GetDeliveriesByUserQuery(
        Long userId
) {
}

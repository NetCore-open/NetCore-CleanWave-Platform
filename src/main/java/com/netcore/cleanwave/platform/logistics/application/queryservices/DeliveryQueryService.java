package com.netcore.cleanwave.platform.logistics.application.queryservices;

import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetAllDeliveriesQuery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetDeliveriesByUserQuery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetDeliveryByIdQuery;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

/**
 * Application service interface for processing queries related to {@link Delivery} aggregates.
 */
@NullMarked
public interface DeliveryQueryService {
    /**
     * Handles the query to retrieve all registered deliveries.
     *
     * @param query the query instance
     * @return the list of all deliveries
     */
    List<Delivery> handle(GetAllDeliveriesQuery query);

    /**
     * Handles the query to retrieve deliveries associated with a specific user.
     *
     * @param query the query containing the user ID
     * @return the list of matching deliveries
     */
    List<Delivery> handle(GetDeliveriesByUserQuery query);

    /**
     * Handles the query to retrieve a single delivery by its identifier.
     *
     * @param query the query containing the delivery ID
     * @return an {@link Optional} containing the delivery if found, empty otherwise
     */
    Optional<Delivery> handle(GetDeliveryByIdQuery query);
}

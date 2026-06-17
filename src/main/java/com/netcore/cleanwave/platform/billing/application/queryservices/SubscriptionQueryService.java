package com.netcore.cleanwave.platform.billing.application.queryservices;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetSubscriptionsByLaundryQuery;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Application service interface for processing queries related to {@link Subscription} aggregates.
 */
@NullMarked
public interface SubscriptionQueryService {
    /**
     * Handles the query to retrieve subscriptions by laundry.
     *
     * @param query the query containing the laundry ID
     * @return the list of subscriptions
     */
    List<Subscription> handle(GetSubscriptionsByLaundryQuery query);
}

package com.netcore.cleanwave.platform.billing.application.internal.queryservices;

import com.netcore.cleanwave.platform.billing.application.queryservices.SubscriptionQueryService;
import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetSubscriptionsByLaundryQuery;
import com.netcore.cleanwave.platform.billing.domain.repositories.SubscriptionRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementation that handles laundry subscription read operations.
 *
 * <p>Implements {@link SubscriptionQueryService} and delegates query logic to the {@link SubscriptionRepository}.</p>
 */
@NullMarked
@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Retrieves all subscriptions registered for a given laundry.
     *
     * @param query the query carrying the laundry ID
     * @return the list of subscriptions matching the laundry ID
     */
    @Override
    public List<Subscription> handle(GetSubscriptionsByLaundryQuery query) {
        return subscriptionRepository.findByLaundryId(query.laundryId());
    }
}

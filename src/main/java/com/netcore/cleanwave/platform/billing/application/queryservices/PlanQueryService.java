package com.netcore.cleanwave.platform.billing.application.queryservices;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetAllPlansQuery;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Application service interface for processing queries related to {@link Plan} aggregates.
 */
@NullMarked
public interface PlanQueryService {
    /**
     * Handles the query to retrieve all subscription plans.
     *
     * @param query the query instance
     * @return the list of subscription plans
     */
    List<Plan> handle(GetAllPlansQuery query);
}

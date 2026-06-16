package com.netcore.cleanwave.platform.billing.application.internal.queryservices;

import com.netcore.cleanwave.platform.billing.application.queryservices.PlanQueryService;
import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetAllPlansQuery;
import com.netcore.cleanwave.platform.billing.domain.repositories.PlanRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementation that handles subscription plan read operations.
 *
 * <p>Implements {@link PlanQueryService} and delegates queries to the {@link PlanRepository}.</p>
 */
@NullMarked
@Service
public class PlanQueryServiceImpl implements PlanQueryService {

    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    /**
     * Retrieves all available subscription plans.
     *
     * @param query the query (no parameters)
     * @return the list of all plans
     */
    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        return planRepository.findAll();
    }
}

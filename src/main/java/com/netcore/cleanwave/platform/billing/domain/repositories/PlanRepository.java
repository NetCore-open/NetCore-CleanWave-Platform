package com.netcore.cleanwave.platform.billing.domain.repositories;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for managing {@link Plan} aggregates.
 */
public interface PlanRepository {
    /**
     * Persists the given subscription plan aggregate.
     *
     * @param plan the plan to save
     * @return the saved plan aggregate
     */
    Plan save(Plan plan);

    /**
     * Retrieves a plan by its unique identifier.
     *
     * @param id the unique identifier of the plan
     * @return an {@link Optional} containing the plan if found, empty otherwise
     */
    Optional<Plan> findById(Long id);

    /**
     * Retrieves all registered subscription plans.
     *
     * @return the list of all plans
     */
    List<Plan> findAll();

    /**
     * Checks if a subscription plan with the given name exists.
     *
     * @param name the plan name to check
     * @return {@code true} if a plan with that name exists, {@code false} otherwise
     */
    boolean existsByName(String name);
}

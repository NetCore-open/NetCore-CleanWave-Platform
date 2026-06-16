package com.netcore.cleanwave.platform.billing.interfaces.rest.transform;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Plan;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.PlanResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link Plan} domain aggregate into a
 * {@link PlanResource} REST response resource.
 */
@NullMarked
public class PlanResourceFromEntityAssembler {

    /**
     * Converts a {@link Plan} domain aggregate to a {@link PlanResource}.
     *
     * @param entity the plan domain aggregate
     * @return the corresponding REST resource
     */
    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getType().name(),
                entity.getBillingPeriod(),
                entity.getLaundryFeatures(),
                entity.getClientFeatures(),
                entity.isRecommended()
        );
    }
}

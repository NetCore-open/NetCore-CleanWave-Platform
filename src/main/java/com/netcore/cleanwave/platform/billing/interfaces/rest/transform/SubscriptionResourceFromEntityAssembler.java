package com.netcore.cleanwave.platform.billing.interfaces.rest.transform;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Subscription;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.SubscriptionResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link Subscription} domain aggregate into a
 * {@link SubscriptionResource} REST response resource.
 */
@NullMarked
public class SubscriptionResourceFromEntityAssembler {

    /**
     * Converts a {@link Subscription} domain aggregate to a {@link SubscriptionResource}.
     *
     * @param entity the subscription domain aggregate
     * @return the corresponding REST resource
     */
    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(
                entity.getId(),
                entity.getPlanId(),
                entity.getLaundryId(),
                entity.getStatus().name(),
                entity.getStartDate().toString(),
                entity.getEndDate().toString()
        );
    }
}

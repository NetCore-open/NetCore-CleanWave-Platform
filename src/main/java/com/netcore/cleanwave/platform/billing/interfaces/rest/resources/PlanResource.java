package com.netcore.cleanwave.platform.billing.interfaces.rest.resources;

import java.util.List;

/**
 * REST response resource representation for a subscription plan.
 */
public record PlanResource(
        Long id,
        String name,
        double price,
        String type,
        String billingPeriod,
        List<String> laundryFeatures,
        List<String> clientFeatures,
        boolean recommended
) {
}

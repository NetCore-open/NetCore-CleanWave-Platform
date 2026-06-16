package com.netcore.cleanwave.platform.billing.interfaces.rest;

import com.netcore.cleanwave.platform.billing.application.queryservices.PlanQueryService;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetAllPlansQuery;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.PlanResource;
import com.netcore.cleanwave.platform.billing.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing subscription plan endpoints.
 *
 * <p>Handles HTTP requests to retrieve all subscription plans available on the platform.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/billing/plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Billing Plans", description = "Subscription plan management endpoints")
public class PlansController {

    private final PlanQueryService planQueryService;

    public PlansController(PlanQueryService planQueryService) {
        this.planQueryService = planQueryService;
    }

    /**
     * Retrieves all subscription plans.
     *
     * @return {@code 200 OK} with the list of plans
     */
    @GetMapping
    public ResponseEntity<List<PlanResource>> getAllPlans() {
        var query = new GetAllPlansQuery();
        var plans = planQueryService.handle(query);
        var resources = plans.stream()
                .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}

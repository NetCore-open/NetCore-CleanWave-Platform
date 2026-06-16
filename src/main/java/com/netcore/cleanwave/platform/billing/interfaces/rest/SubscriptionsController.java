package com.netcore.cleanwave.platform.billing.interfaces.rest;

import com.netcore.cleanwave.platform.billing.application.commandservices.SubscriptionCommandService;
import com.netcore.cleanwave.platform.billing.application.queryservices.SubscriptionQueryService;
import com.netcore.cleanwave.platform.billing.domain.model.commands.CancelSubscriptionCommand;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetSubscriptionsByLaundryQuery;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.CreateSubscriptionResource;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.SubscriptionResource;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.UpdateSubscriptionStatusResource;
import com.netcore.cleanwave.platform.billing.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.billing.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing laundry subscription management endpoints.
 *
 * <p>Handles HTTP requests to create, retrieve, and update laundry subscriptions.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/billing/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Billing Subscriptions", description = "Subscription management endpoints")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    /**
     * Creates a new subscription.
     *
     * @param resource the request resource carrying plan and laundry IDs
     * @return {@code 201 Created} with the subscription resource, or an error response
     */
    @PostMapping
    public ResponseEntity<?> createSubscription(@RequestBody CreateSubscriptionResource resource) {
        var command = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Retrieves all subscriptions registered for a given laundry.
     *
     * @param laundryId the unique identifier of the laundry
     * @return {@code 200 OK} with the list of subscriptions
     */
    @GetMapping
    public ResponseEntity<List<SubscriptionResource>> getSubscriptionsByLaundry(@RequestParam Long laundryId) {
        var query = new GetSubscriptionsByLaundryQuery(laundryId);
        var subscriptions = subscriptionQueryService.handle(query);
        var resources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Updates the status of a subscription (e.g. to CANCELLED).
     *
     * @param subscriptionId the unique identifier of the subscription
     * @param resource       the resource containing the target status
     * @return {@code 200 OK} with the updated subscription, or an error response
     */
    @PatchMapping("/{subscriptionId}")
    public ResponseEntity<?> updateSubscriptionStatus(
            @PathVariable Long subscriptionId,
            @RequestBody UpdateSubscriptionStatusResource resource
    ) {
        if ("CANCELLED".equalsIgnoreCase(resource.status())) {
            var command = new CancelSubscriptionCommand(subscriptionId);
            var result = subscriptionCommandService.handle(command);
            return ResponseEntityAssembler.toResponseEntityFromResult(
                    result,
                    SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                    HttpStatus.OK
            );
        }
        return ResponseEntity.badRequest().body("Invalid status transition. Only 'CANCELLED' is supported.");
    }
}

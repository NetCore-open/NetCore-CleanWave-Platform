package com.netcore.cleanwave.platform.logistics.interfaces.rest;

import com.netcore.cleanwave.platform.logistics.application.commandservices.DeliveryCommandService;
import com.netcore.cleanwave.platform.logistics.application.queryservices.DeliveryQueryService;
import com.netcore.cleanwave.platform.logistics.domain.model.commands.DeleteDeliveryCommand;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetAllDeliveriesQuery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetDeliveriesByUserQuery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetDeliveryByIdQuery;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.resources.CreateDeliveryResource;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.resources.DeliveryResource;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.resources.UpdateDeliveryStatusResource;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.transform.CreateDeliveryCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.transform.DeliveryResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.logistics.interfaces.rest.transform.UpdateDeliveryStatusCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing logistics delivery management endpoints.
 *
 * <p>Handles HTTP requests to create, retrieve, update, and delete logistics tasks.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/logistics/deliveries", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Logistics Deliveries", description = "Delivery and pickup logistics endpoints")
public class DeliveriesController {

    private final DeliveryCommandService deliveryCommandService;
    private final DeliveryQueryService deliveryQueryService;

    public DeliveriesController(DeliveryCommandService deliveryCommandService,
                                DeliveryQueryService deliveryQueryService) {
        this.deliveryCommandService = deliveryCommandService;
        this.deliveryQueryService = deliveryQueryService;
    }

    /**
     * Creates a new delivery task (pickup or delivery).
     *
     * @param resource the request resource containing task parameters
     * @return {@code 201 Created} with the created delivery, or an error response
     */
    @PostMapping
    public ResponseEntity<?> createDelivery(@RequestBody CreateDeliveryResource resource) {
        var command = CreateDeliveryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = deliveryCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                DeliveryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Retrieves deliveries. Can filter by customer user ID if provided.
     *
     * @param userId optional customer user ID query parameter
     * @return {@code 200 OK} with the list of matching deliveries
     */
    @GetMapping
    public ResponseEntity<List<DeliveryResource>> getDeliveries(@RequestParam(required = false) @Nullable Long userId) {
        List<com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery> deliveries;
        if (userId != null) {
            var query = new GetDeliveriesByUserQuery(userId);
            deliveries = deliveryQueryService.handle(query);
        } else {
            var query = new GetAllDeliveriesQuery();
            deliveries = deliveryQueryService.handle(query);
        }
        var resources = deliveries.stream()
                .map(DeliveryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves a single delivery task by its identifier.
     *
     * @param deliveryId the unique identifier of the delivery
     * @return {@code 200 OK} with the delivery details, or {@code 404 Not Found}
     */
    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResource> getDeliveryById(@PathVariable Long deliveryId) {
        var query = new GetDeliveryByIdQuery(deliveryId);
        var deliveryOpt = deliveryQueryService.handle(query);
        return deliveryOpt
                .map(delivery -> ResponseEntity.ok(DeliveryResourceFromEntityAssembler.toResourceFromEntity(delivery)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates a delivery's status and details (e.g. driver assignment).
     *
     * @param deliveryId the unique identifier of the delivery to update
     * @param resource   the request resource carrying status update parameters
     * @return {@code 200 OK} with the updated delivery, or an error response
     */
    @PatchMapping("/{deliveryId}")
    public ResponseEntity<?> updateDeliveryStatus(
            @PathVariable Long deliveryId,
            @RequestBody UpdateDeliveryStatusResource resource
    ) {
        try {
            var command = UpdateDeliveryStatusCommandFromResourceAssembler.toCommandFromResource(deliveryId, resource);
            var result = deliveryCommandService.handle(command);
            return ResponseEntityAssembler.toResponseEntityFromResult(
                    result,
                    DeliveryResourceFromEntityAssembler::toResourceFromEntity,
                    HttpStatus.OK
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid delivery status value.");
        }
    }

    /**
     * Deletes a delivery task.
     *
     * @param deliveryId the unique identifier of the delivery to delete
     * @return {@code 204 No Content} on success, or an error response
     */
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<?> deleteDelivery(@PathVariable Long deliveryId) {
        var command = new DeleteDeliveryCommand(deliveryId);
        var result = deliveryCommandService.handle(command);
        if (result.isSuccess()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getFailure().message());
    }
}

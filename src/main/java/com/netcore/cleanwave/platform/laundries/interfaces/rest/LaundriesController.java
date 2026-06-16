package com.netcore.cleanwave.platform.laundries.interfaces.rest;

import com.netcore.cleanwave.platform.laundries.application.commandservices.LaundryCommandService;
import com.netcore.cleanwave.platform.laundries.application.queryservices.LaundryQueryService;
import com.netcore.cleanwave.platform.laundries.domain.model.commands.UpdateLaundryStatusCommand;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetAllLaundriesQuery;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetLaundryByIdQuery;
import com.netcore.cleanwave.platform.laundries.domain.model.valueobjects.LaundryStatus;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.resources.CreateLaundryResource;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.resources.LaundryResource;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.resources.UpdateLaundryStatusResource;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.transform.CreateLaundryCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.transform.LaundryResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller exposing REST endpoints for managing laundries.
 */
@RestController
@RequestMapping(value = "/api/v1/laundries", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Laundries", description = "Laundry management endpoints")
public class LaundriesController {

    private final LaundryCommandService laundryCommandService;
    private final LaundryQueryService laundryQueryService;

    public LaundriesController(LaundryCommandService laundryCommandService, LaundryQueryService laundryQueryService) {
        this.laundryCommandService = laundryCommandService;
        this.laundryQueryService = laundryQueryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLaundry(@RequestBody CreateLaundryResource resource) {
        var command = CreateLaundryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = laundryCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                LaundryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<LaundryResource>> getAllLaundries() {
        var query = new GetAllLaundriesQuery();
        var laundries = laundryQueryService.handle(query);
        var resources = laundries.stream()
                .map(LaundryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{laundryId}")
    public ResponseEntity<LaundryResource> getLaundryById(@PathVariable Long laundryId) {
        var query = new GetLaundryByIdQuery(laundryId);
        var laundryOpt = laundryQueryService.handle(query);
        return laundryOpt
                .map(laundry -> ResponseEntity.ok(LaundryResourceFromEntityAssembler.toResourceFromEntity(laundry)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{laundryId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateLaundryStatus(
            @PathVariable Long laundryId,
            @RequestBody UpdateLaundryStatusResource resource
    ) {
        try {
            var status = LaundryStatus.valueOf(resource.status().toUpperCase());
            var command = new UpdateLaundryStatusCommand(laundryId, status);
            var result = laundryCommandService.handle(command);
            return ResponseEntityAssembler.toResponseEntityFromResult(
                    result,
                    LaundryResourceFromEntityAssembler::toResourceFromEntity,
                    HttpStatus.OK
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid laundry status. Must be OPEN or CLOSED.");
        }
    }
}

package com.netcore.cleanwave.platform.profiles.interfaces.rest;

import com.netcore.cleanwave.platform.profiles.application.commandservices.ProfileCommandService;
import com.netcore.cleanwave.platform.profiles.application.queryservices.ProfileQueryService;
import com.netcore.cleanwave.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.netcore.cleanwave.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.netcore.cleanwave.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import com.netcore.cleanwave.platform.profiles.interfaces.rest.resources.ProfileResource;
import com.netcore.cleanwave.platform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing profile management endpoints.
 *
 * <p>Handles HTTP requests for creating and retrieving user profiles.
 * Delegates business logic to the {@link ProfileCommandService} and
 * {@link ProfileQueryService}, and translates domain results to HTTP
 * responses via assemblers.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile management endpoints")
public class ProfilesController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;


    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    /**
     * Creates a new profile.
     *
     * @param resource the request body containing profile creation data
     * @return {@code 201 Created} with the created profile resource,
     *         or an error response if validation or business rules fail
     */
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileResource resource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = profileCommandService.handle(createProfileCommand);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Retrieves a profile by its unique identifier.
     *
     * @param profileId the profile's persistence identity
     * @return {@code 200 OK} with the profile resource,
     *         or {@code 404 Not Found} if no profile exists with that id
     */
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    /**
     * Retrieves all profiles.
     *
     * @return {@code 200 OK} with the list of all profile resources
     */
    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var getAllProfilesQuery = new GetAllProfilesQuery();
        var profiles = profileQueryService.handle(getAllProfilesQuery);
        var profileResources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(profileResources);
    }
}

package com.netcore.cleanwave.platform.iam.interfaces.rest;

import com.netcore.cleanwave.platform.iam.application.commandservices.UserCommandService;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.SignInResource;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.SignUpResource;
import com.netcore.cleanwave.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.profiles.application.queryservices.ProfileQueryService;
import com.netcore.cleanwave.platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing authentication endpoints.
 *
 * <p>Handles user sign-up and sign-in requests. Delegates authentication
 * logic to {@link UserCommandService} and translates domain results to
 * HTTP responses via assemblers.</p>
 *
 * <p>This controller does not require prior authentication; it is the
 * entry point for users establishing their session token.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication and authorization management endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;
    private final ProfileQueryService profileQueryService;

    public AuthenticationController(UserCommandService userCommandService,
                                    ProfileQueryService profileQueryService) {
        this.userCommandService = userCommandService;
        this.profileQueryService = profileQueryService;
    }

    /**
     * Registers a new user account.
     *
     * @param resource the request body containing the username, password and optional roles
     * @return {@code 201 Created} with the created user resource,
     *         or an error response if the username is already taken
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpResource resource) {
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Authenticates an existing user and issues a JWT token.
     *
     * @param resource the request body containing the username and password
     * @return {@code 200 OK} with the authenticated user resource including the JWT token,
     *         or an error response if the credentials are invalid
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                userAndToken -> {
                    var email = userAndToken.user().getUsername();
                    var profileOpt = profileQueryService.handle(new GetProfileByEmailQuery(new EmailAddress(email)));
                    var firstName = profileOpt.map(p -> p.getName().firstName()).orElse("");
                    var lastName = profileOpt.map(p -> p.getName().lastName()).orElse("");
                    return AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(userAndToken, firstName, lastName);
                },
                HttpStatus.OK
        );
    }
}

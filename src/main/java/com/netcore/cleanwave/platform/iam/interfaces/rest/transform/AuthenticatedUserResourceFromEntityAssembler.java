package com.netcore.cleanwave.platform.iam.interfaces.rest.transform;

import com.netcore.cleanwave.platform.iam.application.commandservices.UserCommandService.UserAndToken;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link UserAndToken} pair into an
 * {@link AuthenticatedUserResource} REST response resource.
 *
 * <p>Used after a successful sign-in to bundle the user data and the
 * generated JWT token into a single response. Role names have their
 * {@code ROLE_} prefix removed before inclusion.</p>
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point.</p>
 */
@NullMarked
public class AuthenticatedUserResourceFromEntityAssembler {

    /**
     * Converts the given {@link UserAndToken} to an {@link AuthenticatedUserResource}.
     * Role names have their {@code ROLE_} prefix removed.
     *
     * @param entity the user-and-token pair produced by the sign-in command handler
     * @return the corresponding REST resource
     */
    public static AuthenticatedUserResource toResourceFromEntity(UserAndToken entity, String firstName, String lastName) {
        var roles = entity.user().getRoles().stream()
                .map(role -> role.name().replace("ROLE_", ""))
                .toList();
        return new AuthenticatedUserResource(
                entity.user().getId(),
                entity.user().getUsername(),
                entity.token(),
                roles,
                firstName,
                lastName
        );
    }
}

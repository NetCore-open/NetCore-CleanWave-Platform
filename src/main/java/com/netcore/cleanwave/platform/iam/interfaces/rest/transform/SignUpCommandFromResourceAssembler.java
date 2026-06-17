package com.netcore.cleanwave.platform.iam.interfaces.rest.transform;

import com.netcore.cleanwave.platform.iam.domain.model.commands.SignUpCommand;
import com.netcore.cleanwave.platform.iam.domain.model.valueobjects.Roles;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.SignUpResource;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;

/**
 * Assembler that converts a {@link SignUpResource} REST request resource into
 * a {@link SignUpCommand} domain command.
 *
 * <p>Normalises role strings from the resource by ensuring they carry the
 * {@code ROLE_} prefix required by the {@link Roles} enum. Invalid or
 * unrecognised role strings are silently skipped. If no valid roles are
 * provided, {@link Roles#ROLE_CLIENT} is assigned by default.</p>
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point for inbound sign-up data.</p>
 */
@NullMarked
public class SignUpCommandFromResourceAssembler {

    /**
     * Converts the given {@link SignUpResource} to a {@link SignUpCommand}.
     *
     * @param resource the REST request resource carrying the sign-up data
     * @return the corresponding domain command
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = new ArrayList<Roles>();
        if (resource.roles() != null) {
            for (var roleStr : resource.roles()) {
                try {
                    var formattedRole = roleStr.startsWith("ROLE_") ? roleStr : "ROLE_" + roleStr;
                    roles.add(Roles.valueOf(formattedRole.toUpperCase()));
                } catch (IllegalArgumentException e) {

                }
            }
        }
        if (roles.isEmpty()) {
            roles.add(Roles.ROLE_CLIENT);
        }
        return new SignUpCommand(resource.username(), resource.password(), roles);
    }
}

package com.netcore.cleanwave.platform.iam.interfaces.rest.transform;

import com.netcore.cleanwave.platform.iam.domain.model.commands.SignInCommand;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.SignInResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link SignInResource} REST request resource into
 * a {@link SignInCommand} domain command.
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point for inbound sign-in data.</p>
 */
@NullMarked
public class SignInCommandFromResourceAssembler {

    /**
     * Converts the given {@link SignInResource} to a {@link SignInCommand}.
     *
     * @param resource the REST request resource carrying the sign-in credentials
     * @return the corresponding domain command
     */
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.username(), resource.password());
    }
}

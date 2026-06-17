package com.netcore.cleanwave.platform.iam.interfaces.rest.transform;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.UserResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link User} domain aggregate into a
 * {@link UserResource} REST response resource.
 *
 * <p>Strips the {@code ROLE_} prefix from role names before including
 * them in the resource to present a cleaner API surface.</p>
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point.</p>
 */
@NullMarked
public class UserResourceFromEntityAssembler {

    /**
     * Converts the given {@link User} aggregate to a {@link UserResource}.
     * Role names have their {@code ROLE_} prefix removed.
     *
     * @param entity the user domain aggregate
     * @return the corresponding REST resource
     */
    public static UserResource toResourceFromEntity(User entity) {
        var roles = entity.getRoles().stream()
                .map(role -> role.name().replace("ROLE_", ""))
                .toList();
        return new UserResource(
                entity.getId(),
                entity.getUsername(),
                roles
        );
    }
}

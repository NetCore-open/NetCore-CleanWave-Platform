package com.netcore.cleanwave.platform.iam.interfaces.rest.transform;

import com.netcore.cleanwave.platform.iam.application.commandservices.UserCommandService.UserAndToken;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(UserAndToken entity) {
        var roles = entity.user().getRoles().stream()
                .map(role -> role.name().replace("ROLE_", ""))
                .toList();
        return new AuthenticatedUserResource(
                entity.user().getId(),
                entity.user().getUsername(),
                entity.token(),
                roles
        );
    }
}

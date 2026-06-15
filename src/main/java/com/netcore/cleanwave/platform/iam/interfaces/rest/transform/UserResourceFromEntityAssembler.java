package com.netcore.cleanwave.platform.iam.interfaces.rest.transform;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
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

package com.netcore.cleanwave.platform.iam.interfaces.rest.transform;

import com.netcore.cleanwave.platform.iam.domain.model.commands.SignUpCommand;
import com.netcore.cleanwave.platform.iam.domain.model.valueobjects.Roles;
import com.netcore.cleanwave.platform.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {
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

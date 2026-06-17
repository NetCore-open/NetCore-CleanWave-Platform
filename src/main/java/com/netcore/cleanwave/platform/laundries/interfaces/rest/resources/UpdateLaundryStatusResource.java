package com.netcore.cleanwave.platform.laundries.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateLaundryStatusResource(
        @NotBlank(message = "Status is required") String status
) {
}

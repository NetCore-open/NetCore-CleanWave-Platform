package com.netcore.cleanwave.platform.laundries.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateLaundryResource(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Address is required") String address,
        @NotNull(message = "Rating is required") @PositiveOrZero(message = "Rating must be positive or zero") double rating,
        @NotBlank(message = "Image URL is required") String imageUrl
) {
}

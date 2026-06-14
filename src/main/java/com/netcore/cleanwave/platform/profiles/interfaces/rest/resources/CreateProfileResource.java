package com.netcore.cleanwave.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Resource for creating a profile.
 */
@Schema(
        name = "CreateProfileRequest",
        description = "Request payload for creating a new profile",
        example = "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"street\": \"123 Main St\", \"number\": \"Apt 4\", \"city\": \"Springfield\", \"postalCode\": \"12345\", \"country\": \"USA\"}"
)
public record CreateProfileResource(
        @NotBlank(message = "is required")
        @Schema(description = "Profile first name", example = "Jhon", minLength = 1, maxLength = 50)
        String firstName,

        @NotBlank(message = "is required")
        @Schema(description = "Profile first name", example = "Jhon", minLength = 1, maxLength = 50)
        String lastName,

        @NotBlank(message = "is required")
        @Schema(description = "Profile last name", example = "Deo@gmail.com")
        String email,

        @NotBlank(message = "is required")
        @Schema(description = "Street address", minLength = 1, maxLength = 50)
        String street,

        @Schema(description = "Street number", example = "Apt 4", minLength = 1, maxLength = 50)
        String number,

        @NotBlank(message = "is required")
        @Schema(description = "City name", example = "Springfield", minLength = 1, maxLength = 50)
        String city,

        @NotBlank(message = "is required")
        @Schema(description = "Postal Code", example = "12345678", minLength = 1, maxLength = 20)
        String postalCode,

        @NotBlank(message = "is required")
        @Schema(description = "Country name", example = "USA", minLength = 1, maxLength = 50)
        String country
) {
}


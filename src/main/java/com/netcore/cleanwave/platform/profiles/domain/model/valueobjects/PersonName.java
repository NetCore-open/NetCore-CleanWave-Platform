package com.netcore.cleanwave.platform.profiles.domain.model.valueobjects;

/**
 * PersonName Value Object
 */
public record PersonName(String firstName, String lastName) {
    /**
     * Full name getter
     */
    public String getFullName() { return "%s %s".formatted(firstName, lastName); }

    /**
     * Constructor with validation
     */
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First Name must not be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last Name must not be null or blank");
        }
    }
}


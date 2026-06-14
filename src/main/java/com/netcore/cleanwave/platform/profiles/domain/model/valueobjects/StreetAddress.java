package com.netcore.cleanwave.platform.profiles.domain.model.valueobjects;
/**
 * StreetAddress Value Object
 */
public record StreetAddress(
        String street,
        String number,
        String city,
        String postalCode,
        String country) {

    /**
     * StreetAddress constructor
     * @param street Street
     * @param city City
     * @param postalCode Postal Code
     * @param country country
     */
    public StreetAddress(String street, String city, String postalCode, String country) {
        this(street, null, city, postalCode, country);
    }

    /**
     * Get street address as a single value
     */
    public String getStreetAddress() {
        return "%s %s, %s, %s, %s".formatted(street, number, city, postalCode, country);
    }

    /**
     * Constructor with validation
     */
    public StreetAddress {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street must be not be null or blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City be not be null or blank");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal Code must be not be null or blank");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country must be not be null or blank");
        }
    }

}


package com.netcore.cleanwave.platform.profiles.domain.model.valueobjects;

import jakarta.validation.constraints.Email;

/**
 * EmailAddress value object
 * @param address
 */
public record EmailAddress(@Email String address) {
    public EmailAddress {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("EMail address must not be null or blank");
        }
    }

    public String getAddress() { return address; }
}


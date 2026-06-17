package com.netcore.cleanwave.platform.profiles.interfaces.acl;

/**
 * ACL facade that exposes Profiles bounded context capabilities ot other contexts.
 */
public interface ProfilesContextFacade {

    /**
     *Creates a profile and returns its identifier
     * @return created profile identifier or {@code 0L} when creation fails.
     */
    Long createProfile(String firstName, String lastname, String email,
                       String street, String number, String city,
                       String postalCode, String country);

    /**
     * Fetches a profile identifier by email.
     * @param email profile email address
     * @return profile identifier, or {@code 0L} when not found
     */
    Long fetchProfileIdByEmail(String email);
}


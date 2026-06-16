package com.netcore.cleanwave.platform.iam.infrastructure.hashing.bcrypt;

import com.netcore.cleanwave.platform.iam.application.internal.outboundservices.hashing.HashingService;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * BCrypt-based implementation of the {@link HashingService} outbound service.
 *
 * <p>Delegates password encoding and verification to Spring Security's
 * {@link BCryptPasswordEncoder}. Lives in the infrastructure layer so that
 * the domain model depends only on the {@link HashingService} interface.</p>
 */
@NullMarked
@Service
public class BCryptHashingServiceImpl implements HashingService {
    private final BCryptPasswordEncoder passwordEncoder;

    public BCryptHashingServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Encodes a raw password using BCrypt.
     *
     * @param rawPassword the plain-text password to encode
     * @return the BCrypt-hashed password string
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifies whether a raw password matches a BCrypt-encoded password.
     *
     * @param rawPassword     the plain-text password to check
     * @param encodedPassword the previously BCrypt-encoded password
     * @return {@code true} if the passwords match, {@code false} otherwise
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

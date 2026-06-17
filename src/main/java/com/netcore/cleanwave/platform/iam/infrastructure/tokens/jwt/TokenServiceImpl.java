package com.netcore.cleanwave.platform.iam.infrastructure.tokens.jwt;

import com.netcore.cleanwave.platform.iam.application.internal.outboundservices.tokens.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT-based implementation of the {@link TokenService} outbound service.
 *
 * <p>Generates, parses and validates HMAC-SHA signed JSON Web Tokens (JWTs).
 * The signing key and expiration window are injected from application
 * configuration ({@code authorization.jwt.secret} and
 * {@code authorization.jwt.expiration.days}).</p>
 *
 * <p>This class lives in the infrastructure layer because JWT is a
 * specific technology choice; the domain depends only on the
 * {@link TokenService} interface.</p>
 */
@NullMarked
@Service
public class TokenServiceImpl implements TokenService {

    private final SecretKey secretKey;
    private final long expirationTimeInMs;

    public TokenServiceImpl(
            @Value("${authorization.jwt.secret}") String secret,
            @Value("${authorization.jwt.expiration.days}") long expirationDays
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTimeInMs = expirationDays * 24 * 60 * 60 * 1000;
    }

    /**
     * Generates a signed JWT for the given username.
     *
     * @param username the subject to embed in the token
     * @return the compact, URL-safe JWT string
     */
    @Override
    public String generateToken(String username) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + expirationTimeInMs);
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extracts the username (subject) from a valid JWT.
     *
     * @param token the compact JWT string
     * @return the username embedded in the token's subject claim
     */
    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Validates a JWT by verifying its signature and checking that it is not expired.
     *
     * @param token the compact JWT string to validate
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

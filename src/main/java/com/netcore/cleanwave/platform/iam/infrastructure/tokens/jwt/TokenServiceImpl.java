package com.netcore.cleanwave.platform.iam.infrastructure.tokens.jwt;

import com.netcore.cleanwave.platform.iam.application.internal.outboundservices.tokens.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

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

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

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

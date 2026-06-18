package com.netcore.cleanwave.platform.iam.infrastructure.security.filters;

import com.netcore.cleanwave.platform.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Spring Security filter that validates JWT tokens on every incoming HTTP request.
 *
 * <p>Intercepts the {@code Authorization: Bearer <token>} header, validates
 * the JWT via {@link com.netcore.cleanwave.platform.iam.application.internal.outboundservices.tokens.TokenService},
 * and populates the {@link org.springframework.security.core.context.SecurityContext}
 * if the token is valid. Requests without a valid token are passed through
 * unauthenticated and handled by downstream security rules.</p>
 */
@Component
@NullMarked
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.substring(7);
        if (tokenService.validateToken(token)) {
            try {
                var username = tokenService.getUsernameFromToken(token);
                var userDetails = userDetailsService.loadUserByUsername(username);

                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                // Si el token es válido pero el usuario ya no existe en la base de datos (ej. se borró o limpió la BD),
                // ignoramos el token y dejamos que la petición continúe como "no autenticada" 
                // para que no reviente con un error 403/500 en endpoints públicos.
            }
        }

        filterChain.doFilter(request, response);
    }
}

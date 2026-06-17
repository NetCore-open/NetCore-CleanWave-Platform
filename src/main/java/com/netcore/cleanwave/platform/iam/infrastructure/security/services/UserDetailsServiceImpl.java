package com.netcore.cleanwave.platform.iam.infrastructure.security.services;

import com.netcore.cleanwave.platform.iam.domain.repositories.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security {@link UserDetailsService} implementation for the IAM context.
 *
 * <p>Loads user details from the domain's {@link UserRepository} during the
 * JWT authentication flow. Converts the domain {@code User} aggregate's roles
 * into Spring Security {@link SimpleGrantedAuthority} instances so that
 * method-level access control ({@code @PreAuthorize}) can function correctly.</p>
 */
@NullMarked
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user's Spring Security {@link UserDetails} by username.
     *
     * @param username the username to look up
     * @return the corresponding {@link UserDetails} instance
     * @throws UsernameNotFoundException if no user exists with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

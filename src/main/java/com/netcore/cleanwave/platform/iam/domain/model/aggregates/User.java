package com.netcore.cleanwave.platform.iam.domain.model.aggregates;

import com.netcore.cleanwave.platform.iam.domain.model.valueobjects.Roles;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;

import java.util.HashSet;
import java.util.Set;

/**
 * Aggregate root for the IAM (Identity and Access Management) bounded context.
 *
 * <p>Represents a platform user identified by a unique username. Holds the
 * hashed password and the set of roles that govern the user's access rights
 * across the platform.</p>
 *
 * <p>JPA persistence concerns are intentionally kept out of this class;
 * they live in the infrastructure layer's {@code UserPersistenceEntity}.</p>
 */
@NullMarked
public class User extends AbstractDomainAggregateRoot<User> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private String username;

    @Getter
    private String password;

    @Getter
    private Set<Roles> roles;

    /**
     * Default constructor. Initialises the user with an empty role set.
     */
    public User() {
        this.roles = new HashSet<>();
    }

    /**
     * Creates a user with the given username and hashed password.
     * Roles must be assigned separately via {@link #addRole(Roles)}.
     *
     * @param username the unique username
     * @param password the already-hashed password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    /**
     * Creates a user with the given username, hashed password and initial roles.
     *
     * @param username the unique username
     * @param password the already-hashed password
     * @param roles    the initial set of roles; {@code null} is treated as an empty set
     */
    public User(String username, String password, Set<Roles> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles != null ? roles : new HashSet<>();
    }

    /**
     * Adds a role to this user's role set.
     *
     * @param role the role to add
     * @return this aggregate instance (fluent API)
     */
    public User addRole(Roles role) {
        this.roles.add(role);
        return this;
    }
}

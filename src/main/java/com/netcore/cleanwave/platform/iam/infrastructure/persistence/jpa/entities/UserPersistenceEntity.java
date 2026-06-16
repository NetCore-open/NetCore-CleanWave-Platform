package com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.iam.domain.model.valueobjects.Roles;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA persistence entity for the
 * {@link com.netcore.cleanwave.platform.iam.domain.model.aggregates.User}
 * domain aggregate.
 *
 * <p>Maps the user's credentials and roles to the {@code users} and
 * {@code user_roles} database tables. Identity and auditing fields are
 * inherited from {@link AuditableAbstractPersistenceEntity}.</p>
 *
 * <p>This class intentionally lives in the infrastructure layer to keep
 * JPA concerns out of the domain model.</p>
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class UserPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Roles> roles = new HashSet<>();

    public UserPersistenceEntity() {}

    public UserPersistenceEntity(String username, String password, Set<Roles> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}

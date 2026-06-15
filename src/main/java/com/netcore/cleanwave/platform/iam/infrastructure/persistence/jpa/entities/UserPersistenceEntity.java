package com.netcore.cleanwave.platform.iam.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.iam.domain.model.valueobjects.Roles;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

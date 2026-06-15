package com.netcore.cleanwave.platform.iam.domain.model.aggregates;

import com.netcore.cleanwave.platform.iam.domain.model.valueobjects.Roles;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public User(String username, String password, Set<Roles> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles != null ? roles : new HashSet<>();
    }

    public User addRole(Roles role) {
        this.roles.add(role);
        return this;
    }
}

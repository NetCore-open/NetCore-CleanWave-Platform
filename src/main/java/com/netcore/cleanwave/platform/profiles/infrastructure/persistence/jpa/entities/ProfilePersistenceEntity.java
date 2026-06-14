package com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.converters.EmailAddressPersistenceConverter;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.embeddables.StreetAddressPersistenceEmbeddable;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class ProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))})
    private PersonNamePersistenceEmbeddable name;

    @Convert(converter = EmailAddressPersistenceConverter.class)
    private EmailAddress emailAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street_address_street")),
            @AttributeOverride(name = "number", column = @Column(name = "street_address_number")),
            @AttributeOverride(name = "city", column = @Column(name = "street_address_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "street_address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "street_address_country")),})
    private StreetAddressPersistenceEmbeddable streetAddress;

    public ProfilePersistenceEntity() {
    }

    public PersonNamePersistenceEmbeddable getName() {
        return name;
    }

    public void setName(PersonNamePersistenceEmbeddable name) {
        this.name = name;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public StreetAddressPersistenceEmbeddable getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StreetAddressPersistenceEmbeddable streetAddress) {
        this.streetAddress = streetAddress;
    }
}


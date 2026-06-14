package com.netcore.cleanwave.platform.profiles.domain.model.aggregates;

import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.profiles.domain.model.events.ProfileCreatedEvent;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.PersonName;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.StreetAddress;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Profile extends AbstractDomainAggregateRoot<Profile> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private PersonName name;
    private EmailAddress emailAddress;
    private StreetAddress streetAddress;

    /**
     * Creates a profile from the provided domain values
     */
    public Profile(Long id, PersonName name, EmailAddress emailAddress, StreetAddress streetAddress) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.emailAddress = Objects.requireNonNull(emailAddress, "emailAddress must not be null");
        this.streetAddress = Objects.requireNonNull(streetAddress, "streetAddress must not be null");
    }

    /**
     * Creates a profile from the provided domain values
     */
    public Profile(PersonName name, EmailAddress emailAddress, StreetAddress streetAddress) {
        this(null, name, emailAddress, streetAddress);
    }

    /**
     * Constructor with first name, last name, email, street, number, city, postal code and country.
     */
    public Profile(String firstName, String lastName, String email, String street, String number, String city,
                   String postalCode, String country) {
        this ( new PersonName(firstName, lastName),
                new EmailAddress(email),
                new StreetAddress(street, number, city, postalCode, country));
    }

    /**
     * Constructor with a CreateProfileCommand
     */
    public Profile(CreateProfileCommand command) {
        this(   command.firstName(), command.lastName(),
                command.email(),
                command.street(), command.number(), command.city(), command.postalCode(), command.country());
    }
    public void setName(PersonName name) { this.name = Objects.requireNonNull(name, "name must not be null"); }

    public EmailAddress getEmailAddressValue() { return emailAddress; }
    public StreetAddress getStreetAddressValue() { return streetAddress; }
    public String getFullName() { return name.getFullName(); }
    public String getEmailAddress() { return emailAddress.address(); }
    public String getStreetAddress() { return streetAddress.getStreetAddress(); }

    /**
     * Signals that this profile has just been created and persisted.
     *
     * <p>Called by the repository adapter after the JPA identity has been assigned.
     * Registers a {@link ProfileCreatedEvent} so the infrastructure can publish it
     * to interested subscribers in other bounded contexts.</p>
     */
    public void onCreated() { registerDomainEvent(ProfileCreatedEvent.from(this));}
}


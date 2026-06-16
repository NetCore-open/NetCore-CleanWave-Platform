package com.netcore.cleanwave.platform.profiles.domain.model.aggregates;

import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.profiles.domain.model.events.ProfileCreatedEvent;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.PersonName;
import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.StreetAddress;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

/**
 * Aggregate root for the Profiles bounded context.
 *
 * <p>Encapsulates the identity, name, contact email and postal address of a platform
 * user. As an aggregate root it owns the invariants that govern those value objects
 * and registers a {@link ProfileCreatedEvent} after it is persisted so that
 * other bounded contexts can react to its creation.</p>
 *
 * <p>JPA persistence concerns are intentionally kept out of this class;
 * they live in the infrastructure layer's {@code ProfilePersistenceEntity}.</p>
 */
@NullMarked
public class Profile extends AbstractDomainAggregateRoot<Profile> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private PersonName name;
    private EmailAddress emailAddress;
    private StreetAddress streetAddress;

    /**
     * Reconstructs an existing profile from its full set of domain values,
     * including a known persistence identity.
     *
     * @param id            the persistence identity already assigned to this profile
     * @param name          the person's full name
     * @param emailAddress  the contact email address
     * @param streetAddress the postal address
     */
    public Profile(Long id, PersonName name, EmailAddress emailAddress, StreetAddress streetAddress) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.emailAddress = Objects.requireNonNull(emailAddress, "emailAddress must not be null");
        this.streetAddress = Objects.requireNonNull(streetAddress, "streetAddress must not be null");
    }

    /**
     * Creates a new profile from domain value objects without an assigned identity.
     * The identity will be set by the infrastructure layer after persistence.
     *
     * @param name          the person's full name
     * @param emailAddress  the contact email address
     * @param streetAddress the postal address
     */
    public Profile(PersonName name, EmailAddress emailAddress, StreetAddress streetAddress) {
        this(null, name, emailAddress, streetAddress);
    }

    /**
     * Convenience constructor that builds the profile from raw string primitives,
     * delegating value-object construction and invariant validation to the
     * corresponding value objects.
     *
     * @param firstName  the person's first name
     * @param lastName   the person's last name
     * @param email      the contact email address
     * @param street     the street name
     * @param number     the street number
     * @param city       the city name
     * @param postalCode the postal / ZIP code
     * @param country    the country name
     */
    public Profile(String firstName, String lastName, String email, String street, String number, String city,
                   String postalCode, String country) {
        this ( new PersonName(firstName, lastName),
                new EmailAddress(email),
                new StreetAddress(street, number, city, postalCode, country));
    }

    /**
     * Creates a profile from a {@link CreateProfileCommand}.
     * Delegates to the raw-string primitive constructor.
     *
     * @param command the create-profile command carrying the profile data
     */
    public Profile(CreateProfileCommand command) {
        this(   command.firstName(), command.lastName(),
                command.email(),
                command.street(), command.number(), command.city(), command.postalCode(), command.country());
    }

    /**
     * Updates the person's name.
     *
     * @param name the new name; must not be {@code null}
     */
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




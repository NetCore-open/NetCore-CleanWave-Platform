package com.netcore.cleanwave.platform.laundries.domain.model.aggregates;

import com.netcore.cleanwave.platform.laundries.domain.model.commands.CreateLaundryCommand;
import com.netcore.cleanwave.platform.laundries.domain.model.valueobjects.LaundryStatus;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;

/**
 * Aggregate root for the Laundries bounded context.
 *
 * <p>Represents a laundry service registered on the platform, holding its
 * name, physical address, rating, representative image URL and operational
 * status. Status transitions are performed through
 * {@link #updateStatus(LaundryStatus)}.</p>
 *
 * <p>JPA persistence concerns are intentionally kept out of this class;
 * they live in the infrastructure layer's {@code LaundryPersistenceEntity}.</p>
 */
@NullMarked
public class Laundry extends AbstractDomainAggregateRoot<Laundry> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String address;

    @Getter
    private double rating;

    @Getter
    private String imageUrl;

    @Getter
    private LaundryStatus status;

    /**
     * Default constructor. Initialises the laundry with {@link LaundryStatus#OPEN} status.
     */
    public Laundry() {
        this.status = LaundryStatus.OPEN;
    }

    /**
     * Creates a laundry from its primitive field values.
     * Status is initialised to {@link LaundryStatus#OPEN}.
     *
     * @param name     the laundry's display name
     * @param address  the physical address
     * @param rating   the initial rating score
     * @param imageUrl the URL of the laundry's cover image
     */
    public Laundry(String name, String address, double rating, String imageUrl) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.status = LaundryStatus.OPEN;
    }

    /**
     * Creates a laundry from a {@link CreateLaundryCommand}, delegating
     * field mapping to the command record.
     *
     * @param command the create-laundry command
     */
    public Laundry(CreateLaundryCommand command) {
        this.name = command.name();
        this.address = command.address();
        this.rating = command.rating();
        this.imageUrl = command.imageUrl();
        this.status = LaundryStatus.OPEN;
    }

    /**
     * Updates this laundry's operational status.
     *
     * @param newStatus the new status to apply
     * @return this aggregate instance (fluent API)
     */
    public Laundry updateStatus(LaundryStatus newStatus) {
        this.status = newStatus;
        return this;
    }
}

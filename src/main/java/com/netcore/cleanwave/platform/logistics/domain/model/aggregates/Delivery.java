package com.netcore.cleanwave.platform.logistics.domain.model.aggregates;

import com.netcore.cleanwave.platform.logistics.domain.model.commands.CreateDeliveryCommand;
import com.netcore.cleanwave.platform.logistics.domain.model.valueobjects.DeliveryStatus;
import com.netcore.cleanwave.platform.logistics.domain.model.valueobjects.DeliveryType;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

/**
 * Aggregate root for the Logistics bounded context.
 *
 * <p>Represents a pickup or delivery task registered on the platform, carrying its target order,
 * destination address, scheduling date, status, assigned driver, and operational notes.</p>
 */
@NullMarked
public class Delivery extends AbstractDomainAggregateRoot<Delivery> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private Long orderId;

    @Getter
    private Long userId;

    @Getter
    private DeliveryType type;

    @Getter
    private DeliveryStatus status;

    @Getter
    private String address;

    @Getter
    private LocalDate scheduledDate;

    @Getter
    @Nullable
    private String driverName;

    @Getter
    @Nullable
    private String driverPhone;

    @Getter
    @Nullable
    private String notes;

    @Getter
    @Setter
    @Nullable
    private java.util.Date createdAt;

    @Getter
    @Setter
    @Nullable
    private java.util.Date updatedAt;

    /**
     * Default constructor. Initialises the delivery status to {@link DeliveryStatus#PENDING}.
     */
    public Delivery() {
        this.status = DeliveryStatus.PENDING;
    }

    /**
     * Creates a Delivery instance with individual attributes.
     *
     * @param orderId       the unique identifier of the associated order
     * @param userId        the unique identifier of the user requesting the delivery
     * @param type          the delivery operation type (PICKUP or DELIVERY)
     * @param address       the physical address for the logisitcs task
     * @param scheduledDate the planned date for the task
     * @param driverName    the assigned driver's name (can be null)
     * @param driverPhone   the assigned driver's contact number (can be null)
     * @param notes         custom notes/instructions (can be null)
     */
    public Delivery(Long orderId, Long userId, DeliveryType type, String address, LocalDate scheduledDate,
                    @Nullable String driverName, @Nullable String driverPhone, @Nullable String notes) {
        this.orderId = orderId;
        this.userId = userId;
        this.type = type;
        this.address = address;
        this.scheduledDate = scheduledDate;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.notes = notes;
        this.status = DeliveryStatus.PENDING;
    }

    /**
     * Creates a Delivery instance from a {@link CreateDeliveryCommand}.
     *
     * @param command the command containing creation parameters
     */
    public Delivery(CreateDeliveryCommand command) {
        this.orderId = command.orderId();
        this.userId = command.userId();
        this.type = DeliveryType.valueOf(command.type().toUpperCase());
        this.address = command.address();
        this.scheduledDate = command.scheduledDate();
        this.notes = command.notes();
        this.status = DeliveryStatus.PENDING;
    }

    /**
     * Updates the delivery's status and details (e.g. driver assignment).
     *
     * @param newStatus   the new status to apply
     * @param driverName  the driver's name (can be null)
     * @param driverPhone the driver's phone number (can be null)
     * @return this aggregate instance (fluent API)
     */
    public Delivery updateStatus(DeliveryStatus newStatus, @Nullable String driverName, @Nullable String driverPhone) {
        this.status = newStatus;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        return this;
    }
}

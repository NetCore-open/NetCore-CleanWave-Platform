package com.netcore.cleanwave.platform.laundries.domain.model.aggregates;

import com.netcore.cleanwave.platform.laundries.domain.model.commands.CreateLaundryCommand;
import com.netcore.cleanwave.platform.laundries.domain.model.valueobjects.LaundryStatus;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

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

    public Laundry() {
        this.status = LaundryStatus.OPEN;
    }

    public Laundry(String name, String address, double rating, String imageUrl) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.status = LaundryStatus.OPEN;
    }

    public Laundry(CreateLaundryCommand command) {
        this.name = command.name();
        this.address = command.address();
        this.rating = command.rating();
        this.imageUrl = command.imageUrl();
        this.status = LaundryStatus.OPEN;
    }

    public Laundry updateStatus(LaundryStatus newStatus) {
        this.status = newStatus;
        return this;
    }
}

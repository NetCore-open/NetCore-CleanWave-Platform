package com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.entities;

import com.netcore.cleanwave.platform.laundries.domain.model.valueobjects.LaundryStatus;
import com.netcore.cleanwave.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the
 * {@link com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry}
 * domain aggregate.
 *
 * <p>Maps the laundry's name, address, rating, image URL and operational status
 * to the {@code laundries} database table. Identity and auditing fields are
 * inherited from {@link AuditableAbstractPersistenceEntity}.</p>
 *
 * <p>This class intentionally lives in the infrastructure layer to keep
 * JPA concerns out of the domain model.</p>
 */
@Entity
@Table(name = "laundries")
@Getter
@Setter
public class LaundryPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double rating;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LaundryStatus status;

    public LaundryPersistenceEntity() {
    }

    public LaundryPersistenceEntity(String name, String address, double rating, String imageUrl, LaundryStatus status) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.status = status;
    }
}

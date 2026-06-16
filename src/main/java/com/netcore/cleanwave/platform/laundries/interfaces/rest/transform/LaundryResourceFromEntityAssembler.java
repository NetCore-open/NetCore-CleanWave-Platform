package com.netcore.cleanwave.platform.laundries.interfaces.rest.transform;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.resources.LaundryResource;

public class LaundryResourceFromEntityAssembler {
    public static LaundryResource toResourceFromEntity(Laundry entity) {
        return new LaundryResource(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getRating(),
                entity.getImageUrl(),
                entity.getStatus().name()
        );
    }
}

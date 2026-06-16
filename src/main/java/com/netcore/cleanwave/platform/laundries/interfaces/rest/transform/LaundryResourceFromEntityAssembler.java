package com.netcore.cleanwave.platform.laundries.interfaces.rest.transform;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.interfaces.rest.resources.LaundryResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link Laundry} domain aggregate into a
 * {@link LaundryResource} REST response resource.
 *
 * <p>Keeps the REST layer decoupled from the domain model by providing
 * a single, explicit translation point.</p>
 */
@NullMarked
public class LaundryResourceFromEntityAssembler {

    /**
     * Converts the given {@link Laundry} aggregate to a {@link LaundryResource}.
     *
     * @param entity the laundry domain aggregate
     * @return the corresponding REST resource
     */
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

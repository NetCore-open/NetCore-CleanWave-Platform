package com.netcore.cleanwave.platform.billing.interfaces.rest.transform;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.TransactionResource;
import org.jspecify.annotations.NullMarked;

/**
 * Assembler that converts a {@link Transaction} domain aggregate into a
 * {@link TransactionResource} REST response resource.
 */
@NullMarked
public class TransactionResourceFromEntityAssembler {

    /**
     * Converts a {@link Transaction} domain aggregate to a {@link TransactionResource}.
     *
     * @param entity the transaction domain aggregate
     * @return the corresponding REST resource
     */
    public static TransactionResource toResourceFromEntity(Transaction entity) {
        return new TransactionResource(
                entity.getId(),
                entity.getSubscriptionId(),
                entity.getAmount(),
                entity.getDate().toString(),
                entity.getStatus().name()
        );
    }
}

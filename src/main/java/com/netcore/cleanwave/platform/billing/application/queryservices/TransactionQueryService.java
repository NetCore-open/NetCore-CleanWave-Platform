package com.netcore.cleanwave.platform.billing.application.queryservices;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetTransactionsBySubscriptionQuery;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Application service interface for processing queries related to {@link Transaction} aggregates.
 */
@NullMarked
public interface TransactionQueryService {
    /**
     * Handles the query to retrieve transactions by subscription.
     *
     * @param query the query containing the subscription ID
     * @return the list of transactions
     */
    List<Transaction> handle(GetTransactionsBySubscriptionQuery query);
}

package com.netcore.cleanwave.platform.billing.application.internal.queryservices;

import com.netcore.cleanwave.platform.billing.application.queryservices.TransactionQueryService;
import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetTransactionsBySubscriptionQuery;
import com.netcore.cleanwave.platform.billing.domain.repositories.TransactionRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementation that handles payment transaction read operations.
 *
 * <p>Implements {@link TransactionQueryService} and delegates query logic to the {@link TransactionRepository}.</p>
 */
@NullMarked
@Service
public class TransactionQueryServiceImpl implements TransactionQueryService {

    private final TransactionRepository transactionRepository;

    public TransactionQueryServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieves all transactions logged under a given subscription.
     *
     * @param query the query containing the target subscription ID
     * @return the list of transactions matching the subscription ID
     */
    @Override
    public List<Transaction> handle(GetTransactionsBySubscriptionQuery query) {
        return transactionRepository.findBySubscriptionId(query.subscriptionId());
    }
}

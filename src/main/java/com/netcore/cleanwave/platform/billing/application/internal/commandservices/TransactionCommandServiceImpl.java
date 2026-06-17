package com.netcore.cleanwave.platform.billing.application.internal.commandservices;

import com.netcore.cleanwave.platform.billing.application.commandservices.TransactionCommandService;
import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;
import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateTransactionCommand;
import com.netcore.cleanwave.platform.billing.domain.repositories.SubscriptionRepository;
import com.netcore.cleanwave.platform.billing.domain.repositories.TransactionRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that handles payment transaction write operations.
 *
 * <p>Implements {@link TransactionCommandService} to orchestrate registration
 * of {@link Transaction} aggregates. Verifies subscription existence prior to saving transaction records.</p>
 */
@NullMarked
@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {

    private final TransactionRepository transactionRepository;
    private final SubscriptionRepository subscriptionRepository;

    public TransactionCommandServiceImpl(TransactionRepository transactionRepository,
                                          SubscriptionRepository subscriptionRepository) {
        this.transactionRepository = transactionRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Handles registration of a new payment transaction.
     *
     * @param command the command containing transaction details
     * @return a success {@link Result} with the created transaction, or a failure {@link Result} with an error
     */
    @Override
    public Result<Transaction, ApplicationError> handle(CreateTransactionCommand command) {
        var subscriptionOpt = subscriptionRepository.findById(command.subscriptionId());
        if (subscriptionOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Subscription", "Subscription not found with ID: " + command.subscriptionId()));
        }

        try {
            var transaction = new Transaction(command);
            var savedTransaction = transactionRepository.save(transaction);
            return Result.success(savedTransaction);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Transaction creation", e.getMessage()));
        }
    }
}

package com.netcore.cleanwave.platform.billing.application.commandservices;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;
import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateTransactionCommand;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;

/**
 * Application service interface for processing commands related to {@link Transaction} aggregates.
 */
@NullMarked
public interface TransactionCommandService {
    /**
     * Handles the command to register/create a new payment transaction.
     *
     * @param command the command containing transaction details
     * @return a {@link Result} carrying the created transaction or an error
     */
    Result<Transaction, ApplicationError> handle(CreateTransactionCommand command);
}

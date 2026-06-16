package com.netcore.cleanwave.platform.billing.domain.model.aggregates;

import com.netcore.cleanwave.platform.billing.domain.model.commands.CreateTransactionCommand;
import com.netcore.cleanwave.platform.billing.domain.model.valueobjects.TransactionStatus;
import com.netcore.cleanwave.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;

/**
 * Aggregate root for the Billing bounded context representing a payment transaction.
 *
 * <p>Logs details of financial transactions related to subscription plans, including
 * amount paid, timestamp, and status.</p>
 */
@NullMarked
public class Transaction extends AbstractDomainAggregateRoot<Transaction> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private Long subscriptionId;

    @Getter
    private double amount;

    @Getter
    private LocalDate date;

    @Getter
    private TransactionStatus status;

    /**
     * Default constructor. Initialises status to {@link TransactionStatus#PENDING}.
     */
    public Transaction() {
        this.status = TransactionStatus.PENDING;
    }

    /**
     * Creates a transaction with individual attributes.
     *
     * @param subscriptionId the unique identifier of the target subscription
     * @param amount         the transaction amount
     * @param date           the date the transaction occurred
     * @param status         the status of the transaction
     */
    public Transaction(Long subscriptionId, double amount, LocalDate date, TransactionStatus status) {
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    /**
     * Creates a transaction from a {@link CreateTransactionCommand}.
     *
     * @param command the command containing creation data
     */
    public Transaction(CreateTransactionCommand command) {
        this.subscriptionId = command.subscriptionId();
        this.amount = command.amount();
        this.date = LocalDate.now();
        this.status = TransactionStatus.COMPLETED;
    }
}

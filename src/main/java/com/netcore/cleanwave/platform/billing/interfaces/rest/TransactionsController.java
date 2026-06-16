package com.netcore.cleanwave.platform.billing.interfaces.rest;

import com.netcore.cleanwave.platform.billing.application.commandservices.TransactionCommandService;
import com.netcore.cleanwave.platform.billing.application.queryservices.TransactionQueryService;
import com.netcore.cleanwave.platform.billing.domain.model.queries.GetTransactionsBySubscriptionQuery;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.CreateTransactionResource;
import com.netcore.cleanwave.platform.billing.interfaces.rest.resources.TransactionResource;
import com.netcore.cleanwave.platform.billing.interfaces.rest.transform.CreateTransactionCommandFromResourceAssembler;
import com.netcore.cleanwave.platform.billing.interfaces.rest.transform.TransactionResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing billing transaction management endpoints.
 *
 * <p>Handles HTTP requests to register payment transactions and query them by subscription.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/billing/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Billing Transactions", description = "Transaction management endpoints")
public class TransactionsController {

    private final TransactionCommandService transactionCommandService;
    private final TransactionQueryService transactionQueryService;

    public TransactionsController(TransactionCommandService transactionCommandService,
                                  TransactionQueryService transactionQueryService) {
        this.transactionCommandService = transactionCommandService;
        this.transactionQueryService = transactionQueryService;
    }

    /**
     * Registers a new transaction.
     *
     * @param resource the request resource carrying subscription ID and payment amount
     * @return {@code 201 Created} with the transaction resource, or an error response
     */
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionResource resource) {
        var command = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = transactionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                TransactionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
            );
    }

    /**
     * Retrieves all transactions logged under a subscription.
     *
     * @param subscriptionId the unique identifier of the subscription
     * @return {@code 200 OK} with the list of transactions
     */
    @GetMapping
    public ResponseEntity<List<TransactionResource>> getTransactionsBySubscription(@RequestParam Long subscriptionId) {
        var query = new GetTransactionsBySubscriptionQuery(subscriptionId);
        var transactions = transactionQueryService.handle(query);
        var resources = transactions.stream()
                .map(TransactionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}

package com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.adapters;

import com.netcore.cleanwave.platform.billing.domain.model.aggregates.Transaction;
import com.netcore.cleanwave.platform.billing.domain.repositories.TransactionRepository;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.assemblers.TransactionPersistenceAssembler;
import com.netcore.cleanwave.platform.billing.infrastructure.persistence.jpa.repositories.TransactionPersistenceRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Infrastructure adapter implementing {@link TransactionRepository} using Spring Data JPA.
 */
@NullMarked
@Component
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionPersistenceRepository transactionPersistenceRepository;

    public TransactionRepositoryImpl(TransactionPersistenceRepository transactionPersistenceRepository) {
        this.transactionPersistenceRepository = transactionPersistenceRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = TransactionPersistenceAssembler.toPersistenceFromDomain(transaction);
        var savedEntity = transactionPersistenceRepository.save(entity);
        return TransactionPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionPersistenceRepository.findById(id)
                .map(TransactionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Transaction> findBySubscriptionId(Long subscriptionId) {
        return transactionPersistenceRepository.findBySubscriptionId(subscriptionId).stream()
                .map(TransactionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}

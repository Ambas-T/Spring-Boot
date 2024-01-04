package com.example.transaction;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class BankServices {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private final PlatformTransactionManager transactionManager;

    private final BankAccountRepository bankAccountRepositoryACID;

    private static final Logger logger = LoggerFactory.getLogger(BankServices.class);

    @Transactional
    public void transferMoneyWithACID(Long fromAccountId, Long toAccountId, Double amount) {
        try {
            validateTransfer(fromAccountId, toAccountId, amount);

            BankAccount fromAccount = bankAccountRepositoryACID.findById(fromAccountId)
                    .orElseThrow(() -> new EntityNotFoundException("Sender account not found."));
            BankAccount toAccount = bankAccountRepositoryACID.findById(toAccountId)
                    .orElseThrow(() -> new EntityNotFoundException("Receiver account not found."));

            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            bankAccountRepositoryACID.save(fromAccount);
            bankAccountRepositoryACID.save(toAccount);

            logger.info("Transfer successful: {} transferred from account {} to account {}", amount, fromAccountId, toAccountId);
        } catch (Exception e) {
            logger.error("Transfer failed: ", e);
            throw e; // Rethrow the exception for the caller to handle appropriately
        }
    }

    private void validateTransfer(Long fromAccountId, Long toAccountId, Double amount) {
        if (fromAccountId == null || toAccountId == null) {
            throw new IllegalArgumentException("Account IDs cannot be null.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Sender and receiver accounts must be different.");
        }
    }

    // This method is intentionally flawed to demonstrate the violation of ACID properties
    public void transferMoneyWithoutACID(Long fromAccountId, Long toAccountId, Double amount) {
        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId).orElse(null);
        BankAccount toAccount = bankAccountRepository.findById(toAccountId).orElse(null);

        if (fromAccount != null && toAccount != null) {

            // Deduct from the sender
            fromAccount.setBalance(fromAccount.getBalance() - amount);

            // Not Atomic: The operation can be incomplete if the method exits early
            // Simulate a failure here (breaking Atomicity)
            if (Math.random() > 0.5) {
                return; // Early return, transaction is not completed
            }

            // Add to the receiver
            toAccount.setBalance(toAccount.getBalance() + amount);

            // Not Consistent: Only the receiver account is updated if the method exits early
            bankAccountRepository.save(toAccount);

            // Not Isolated: No transaction management means other transactions can interfere
            // with the data being manipulated by this transaction before it's finished

            // Not Durable: There are no mechanisms to ensure that the transaction's changes
            // survive in case of a system failure after the method completes
        }
        // Additionally, there is no error handling or rollback mechanism in case of a failure,
        // which further violates the principle of Atomicity
    }


    // Programmatic Transaction Management
    public BankServices(BankAccountRepository bankAccountRepository, PlatformTransactionManager transactionManager) {
        this.bankAccountRepositoryACID = bankAccountRepository;
        this.transactionManager = transactionManager;
    }

    public void transferMoneyWithProgrammaticTx(Long fromAccountId, Long toAccountId, Double amount) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            validateTransfer(fromAccountId, toAccountId, amount);

            BankAccount fromAccount = bankAccountRepositoryACID.findById(fromAccountId)
                    .orElseThrow(() -> new EntityNotFoundException("Sender account not found."));
            BankAccount toAccount = bankAccountRepositoryACID.findById(toAccountId)
                    .orElseThrow(() -> new EntityNotFoundException("Receiver account not found."));

            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            bankAccountRepositoryACID.save(fromAccount);
            bankAccountRepositoryACID.save(toAccount);

            transactionManager.commit(status); // Commit the transaction

            logger.info("Transfer successful: {} transferred from account {} to account {}", amount, fromAccountId, toAccountId);
        } catch (Exception e) {
            transactionManager.rollback(status); // Rollback the transaction on exception
            logger.error("Transfer failed: ", e);
            throw e;
        }
    }
}


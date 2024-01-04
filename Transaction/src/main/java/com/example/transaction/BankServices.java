package com.example.transaction;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankServices {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private final BankAccountRepository bankAccountRepositoryACID;

    private static final Logger logger = LoggerFactory.getLogger(BankServices.class);

    @Autowired
    public BankServices(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepositoryACID = bankAccountRepository;
    }

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

    // This method is intentionally flawed
    public void transferMoneyWithOutACID(Long fromAccountId, Long toAccountId, Double amount) {
        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId).orElse(null);
        BankAccount toAccount = bankAccountRepository.findById(toAccountId).orElse(null);

        if (fromAccount != null && toAccount != null) {
            fromAccount.setBalance(fromAccount.getBalance() - amount); // Deduct from the sender

            // Simulate a failure here (breaking Atomicity)
            if (Math.random() > 0.5) {
                return; // Early return, transaction is not completed
            }

            toAccount.setBalance(toAccount.getBalance() + amount); // Add to the receiver
            bankAccountRepository.save(toAccount); // Only the receiver account is updated (breaking Consistency)

            // No transaction management (breaking Isolation)
            // No durability checks or error handling
        }
    }
}


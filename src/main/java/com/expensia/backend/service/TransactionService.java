package com.expensia.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.expensia.backend.entity.Transaction;
import com.expensia.backend.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Create a new transaction
    public Transaction createTransaction(Transaction transaction) {
        transaction.setId(null);
        return transactionRepository.save(transaction);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Get a transaction by ID
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // Update an existing transaction
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found with ID: " + id));

        existingTransaction.setTitle(updatedTransaction.getTitle());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setCategory(updatedTransaction.getCategory());
        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setDate(updatedTransaction.getDate());
        existingTransaction.setPaymentMethod(updatedTransaction.getPaymentMethod());

        return transactionRepository.save(existingTransaction);
    }

    // Delete a transaction
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with ID: " + id);
        }

        transactionRepository.deleteById(id);
    }
}
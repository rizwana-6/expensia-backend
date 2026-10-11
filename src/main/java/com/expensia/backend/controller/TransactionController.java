package com.expensia.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.expensia.backend.entity.Transaction;
import com.expensia.backend.entity.User;
import com.expensia.backend.repository.UserRepository;
import com.expensia.backend.service.TransactionService;
import com.expensia.backend.repository.TransactionRepository;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionController(
            TransactionService transactionService,
            TransactionRepository transactionRepository,
            UserRepository userRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // Create a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestParam Long userId,
            @RequestBody Transaction transaction) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        transaction.setUser(user.get());

        Transaction createdTransaction =
                transactionService.createTransaction(transaction);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTransaction);
    }

    // Get transactions for one user
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(
            @RequestParam Long userId) {

        List<Transaction> transactions =
                transactionRepository.findByUser_Id(userId);

        return ResponseEntity.ok(transactions);
    }

    // Get a transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable Long id) {

        Optional<Transaction> transaction =
                transactionService.getTransactionById(id);

        return transaction
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing transaction
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {

        try {
            Transaction updatedTransaction =
                    transactionService.updateTransaction(id, transaction);

            return ResponseEntity.ok(updatedTransaction);

        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id) {

        try {
            transactionService.deleteTransaction(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
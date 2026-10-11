package com.expensia.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.expensia.backend.entity.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser_Id(Long userId);

}
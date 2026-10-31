
package com.example.personalfinancetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.personalfinancetracker.entity.Transaction;
import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
	List<Transaction> findByType(String type);
}

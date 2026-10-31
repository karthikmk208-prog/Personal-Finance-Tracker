
package com.example.personalfinancetracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.personalfinancetracker.entity.Transaction;
import com.example.personalfinancetracker.repository.TransactionRepository;

import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }
    public double getTotalIncome() {
        return repository.findAll()
                .stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return repository.findAll()
                .stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpense();
    }
    public double getMonthlyIncome() {
        YearMonth month = YearMonth.now();

        return repository.findAll().stream()
                .filter(t -> t.getDate() != null)
                .filter(t -> YearMonth.from(t.getDate()).equals(month))
                .filter(t -> "INCOME".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getMonthlyExpense() {
        YearMonth month = YearMonth.now();

        return repository.findAll().stream()
                .filter(t -> t.getDate() != null)
                .filter(t -> YearMonth.from(t.getDate()).equals(month))
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    public Map<String, Double> getExpenseByCategory() {

        return repository.findByType("EXPENSE")
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {

        Transaction existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setTitle(updatedTransaction.getTitle());
        existing.setAmount(updatedTransaction.getAmount());
        existing.setType(updatedTransaction.getType());
        existing.setCategory(updatedTransaction.getCategory());
        existing.setDate(updatedTransaction.getDate());

        return repository.save(existing);
    }
}

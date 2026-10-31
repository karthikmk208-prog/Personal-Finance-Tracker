
package com.example.personalfinancetracker.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.*;

import com.example.personalfinancetracker.entity.Transaction;
import com.example.personalfinancetracker.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return service.addTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        service.deleteTransaction(id);
        return "Transaction deleted successfully";
    }
    @GetMapping("/summary")
    public Map<String, Double> getSummary() {

        Map<String, Double> summary = new HashMap<>();

        summary.put("totalIncome", service.getTotalIncome());
        summary.put("totalExpense", service.getTotalExpense());
        summary.put("balance", service.getBalance());

        return summary;
    }
    @PutMapping("/{id}")
    public Transaction updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {

        return service.updateTransaction(id, transaction);
    }
}

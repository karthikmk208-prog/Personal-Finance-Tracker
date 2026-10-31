package com.example.personalfinancetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.personalfinancetracker.service.TransactionService;

@Controller
public class HomeController {

    private final TransactionService service;

    public HomeController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("totalIncome", service.getTotalIncome());
        model.addAttribute("totalExpense", service.getTotalExpense());
        model.addAttribute("balance", service.getBalance());
        model.addAttribute("transactions", service.getAllTransactions());
        model.addAttribute("categoryExpenses", service.getExpenseByCategory());
        model.addAttribute("monthlyIncome", service.getMonthlyIncome());
        model.addAttribute("monthlyExpense", service.getMonthlyExpense());

        return "index";
    }
}
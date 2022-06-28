package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.AddTransferForm;
import com.example.paymybuddy.model.dto.Transaction;
import com.example.paymybuddy.model.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TransactionService {
    Iterable<Transaction> getTransactions();

    Page<Transaction> getTransactionByPage(User user, PageRequest pageRequest);

    void addTransaction(User user, AddTransferForm transaction);

    float calculateTotalFeesAmount(List<Transaction> transactions);
}

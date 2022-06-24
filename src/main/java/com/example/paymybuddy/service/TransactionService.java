package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.AddTransferForm;
import com.example.paymybuddy.model.dto.Transaction;
import com.example.paymybuddy.model.dto.User;

public interface TransactionService {
    Iterable<Transaction> getTransactions();

    void addTransaction(User user, AddTransferForm transaction);

}

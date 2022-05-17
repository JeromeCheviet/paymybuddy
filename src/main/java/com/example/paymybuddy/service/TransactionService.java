package com.example.paymybuddy.service;

import com.example.paymybuddy.model.dto.Transaction;

public interface TransactionService {
    Iterable<Transaction> getTransactions();
}

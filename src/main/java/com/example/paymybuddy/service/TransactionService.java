package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.AddTransferForm;
import com.example.paymybuddy.model.dto.Transaction;
import com.example.paymybuddy.model.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Interface link to the transactions operations.
 */
public interface TransactionService {
    /**
     * Get all transactions
     *
     * @return an iterable list contains all transactions.
     */
    Iterable<Transaction> getTransactions();

    /**
     * Get all transactions contains in a specific page for a specific user.
     *
     * @param user        User object.
     * @param pageRequest page wanted.
     * @return all transactions contained in a page.
     */
    Page<Transaction> getTransactionByPage(User user, PageRequest pageRequest);

    /**
     * Add a new transaction for a specific user.
     *
     * @param user        User object.
     * @param transaction Object which contains transaction information.
     */
    void addTransaction(User user, AddTransferForm transaction);

    /**
     * Calculate the total of fees amount for a list of transaction.
     *
     * @param transactions A list which contain one or many transactions objects
     * @return the total of fees amount in <i>Float</i> format.
     */
    float calculateTotalFeesAmount(List<Transaction> transactions);
}

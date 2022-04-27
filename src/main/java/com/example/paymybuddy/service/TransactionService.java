package com.example.paymybuddy.service;

import com.example.paymybuddy.model.Transaction;
import com.example.paymybuddy.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to interact with transaction table data.
 */
@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Method which returns all data from transaction table
     *
     * @return <b>Transaction</b> : All data in Transaction object.
     */
    public Iterable<Transaction> getTransactions() {
        logger.debug("Get all Transactions.");
        return transactionRepository.findAll();
    }
}

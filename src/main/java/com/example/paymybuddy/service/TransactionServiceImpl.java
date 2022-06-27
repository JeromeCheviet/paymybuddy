package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.AddTransferForm;
import com.example.paymybuddy.model.dto.Transaction;
import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Class to interact with transaction table data.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    /**
     * Method which returns all data from transaction table
     *
     * @return <b>Transaction</b> : All data in Transaction object.
     */
    @Override
    public Iterable<Transaction> getTransactions() {
        logger.debug("Get all Transactions.");
        return transactionRepository.findAll();
    }

    @Override
    public Page<Transaction> getTransactionByPage(User user, PageRequest pageRequest) {
        logger.debug("Get all transaction where userId is {}", user.getEmail());
        return transactionRepository.findByUser(user, pageRequest);
    }

    @Override
    public void addTransaction(User user, AddTransferForm transaction) {
        Transaction newTransaction = new Transaction();
        float totalFee = (transaction.getAmount() * 0.5f) / 100;
        Optional<User> beneficiary = userService.getUserById(transaction.getUserId());

        if (beneficiary.isPresent()) {
            userService.makeTransaction(transaction.getAmount(), user, beneficiary.get());

            newTransaction.setDate(LocalDate.now());
            newTransaction.setUser(user);
            newTransaction.setBeneficiaryUser(beneficiary.get());
            newTransaction.setDescription(transaction.getDescription());
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setFeeAmount(totalFee);

        }

        transactionRepository.save(newTransaction);
    }

}

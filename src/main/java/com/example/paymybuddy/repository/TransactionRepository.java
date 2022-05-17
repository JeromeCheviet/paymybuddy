package com.example.paymybuddy.repository;

import com.example.paymybuddy.model.dto.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries run on the transaction table.
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}

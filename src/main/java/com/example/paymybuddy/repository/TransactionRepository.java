package com.example.paymybuddy.repository;

import com.example.paymybuddy.model.dto.Transaction;
import com.example.paymybuddy.model.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries run on the transaction table.
 */
@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    /**
     * Find all transaction for a user, and make it pageable.
     *
     * @param user     user requested
     * @param pageable page you want and number of row by page. Optionally, type of sorting you want.
     * @return all transaction requested in a Page<> format.
     */
    Page<Transaction> findByUser(User user, Pageable pageable);

}

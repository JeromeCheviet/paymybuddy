package com.example.paymybuddy.repository;

import com.example.paymybuddy.model.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries run on the user table.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    /**
     * find user with specific email
     *
     * @param email user email in String format.
     * @return User information in User class format.
     */
    User findByEmail(String email);

    /**
     * Find all users, and make it pageable.
     * @param pageable page you want and number of row by page. Optionally, type of sorting you want.
     * @return all users requested in a Page<> format.
     */
    Page<User> findAll(Pageable pageable);

}

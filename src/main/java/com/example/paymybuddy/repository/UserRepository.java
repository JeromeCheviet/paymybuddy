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

    public User findByEmail(String email);

    Page<User> findAll(Pageable pageable);

}

package com.example.paymybuddy.repository;

import com.example.paymybuddy.model.dto.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries run on the user table.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByEmail(String email);

}

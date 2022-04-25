package com.example.paymybuddy.service;

import com.example.paymybuddy.model.User;
import com.example.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to interact with user table data.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method which returns all data from user table
     *
     * @return <b>User</b> : All data in User object.
     */
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

}

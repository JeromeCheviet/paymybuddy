package com.example.paymybuddy.service;

import com.example.paymybuddy.model.User;
import com.example.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class to interact with user table data.
 */
@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Method which returns all data from user table
     *
     * @return <b>User</b> : All data in User object.
     */
    public Iterable<User> getUsers() {
        logger.debug("Get all users.");
        return userRepository.findAll();
    }

    /**
     * Method to have user by this id.
     *
     * @param id
     * @return User
     */
    public Optional<User> getUserById(Integer id) {
        logger.debug("Get user with id : " + id);
        return userRepository.findById(id);
    }

    /**
     * Method to have user by this email.
     *
     * @param email
     * @return User
     */
    public User getUserByEmail(String email) {
        logger.debug("Get user with email : " + email);
        return userRepository.findByEmail(email);
    }

}

package com.example.paymybuddy.service;

import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class to interact with user table data.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Method which returns all data from user table
     *
     * @return <b>User</b> : All data in User object.
     */
    @Override
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
    @Override
    public Optional<User> getUserById(Integer id) {
        logger.debug("Get user with id : {} ", id);
        return userRepository.findById(id);
    }

    /**
     * Method to have user by this email.
     *
     * @param email
     * @return User
     */
    @Override
    public User getUserByEmail(String email) {
        logger.debug("Get user with email : {}", email);
        return userRepository.findByEmail(email);
    }

    /**
     * Method to set user balance. You can credit your account from your bank
     * or debit your account to your bank.
     *
     * @param existingUser - <b>User</b> object
     * @param amount       - <b>float</b> amount to credit or debit.
     * @param transferType - <b>String</b> Transaction type.
     */
    @Override
    public void transferFromOrToBank(User existingUser, Float amount, String transferType) {
        Float actualBalance = existingUser.getBalance();
        logger.debug("Actual balance : {}", actualBalance);
        Float newBalance = actualBalance;
        if (transferType.equalsIgnoreCase("credit")) {
            newBalance = actualBalance + amount;
            logger.debug("new balance : {}", newBalance);
        } else if (transferType.equalsIgnoreCase("debit") && (actualBalance > 0)) {
            newBalance = actualBalance - amount;
            logger.debug("new balance : {}", newBalance);
        }
        existingUser.setBalance(newBalance);
        userRepository.save(existingUser);
    }

    @Override
    public void changeUserRole(User existingUser) {
        if (existingUser.isRole()) {
            existingUser.setRole(false);
        } else {
            existingUser.setRole(true);
        }

        userRepository.save(existingUser);
    }

    @Override
    public boolean saveUser(User newUser) {
        System.out.println(newUser.getPassword());
        User existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser != null) {
            logger.info("User no created. Email {} already exist.", newUser.getEmail());
            return false;
        }
        userRepository.save(newUser);
        return true;
    }

    @Override
    public void userDelete(User existingUser) {
        userRepository.delete(existingUser);
    }

}

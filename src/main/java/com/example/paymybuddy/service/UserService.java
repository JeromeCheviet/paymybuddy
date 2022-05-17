package com.example.paymybuddy.service;

import com.example.paymybuddy.model.dto.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> getUsers();

    Optional<User> getUserById(Integer id);

    User getUserByEmail(String email);

    void transferFromOrToBank(User existingUser, Float amount, String transferType);

    void changeUserRole(User existingUser);

    boolean saveUser(User newUser);

    void userDelete(User user);
}

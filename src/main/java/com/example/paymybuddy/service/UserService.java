package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.EditUserForm;
import com.example.paymybuddy.model.application.SignupForm;
import com.example.paymybuddy.model.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Iterable<User> getUsers();

    Page<User> getUsersByPage(PageRequest pageRequest);

    Optional<User> getUserById(Integer id);

    User getUserByEmail(String email);

    void transferFromOrToBank(User existingUser, Float amount, String transferType);

    void changeUserRole(User existingUser);

    boolean saveUser(SignupForm newUser);

    void userDelete(User user);

    void modifyUser(User existingUser, EditUserForm editedUser);

    void addContact(User actualUser, User friendUser);

    void removeContact(User actualUser, User friendUser);

    void makeTransaction(float amount, User user, User beneficiaryUser);

    List<User> allUsersExceptFriends(User user);
}

package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.EditUserForm;
import com.example.paymybuddy.model.application.SignupForm;
import com.example.paymybuddy.model.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * Interface link to user operations.
 */
public interface UserService {
    /**
     * Get all users
     *
     * @return an iterable list contains all transactions.
     */
    Iterable<User> getUsers();

    /**
     * Get all users contains in a specific page.
     *
     * @param pageRequest page wanted.
     * @return all users contained in a page.
     */
    Page<User> getUsersByPage(PageRequest pageRequest);

    /**
     * Get user by this id.
     *
     * @param id User id asked.
     * @return The user object if existing.
     */
    Optional<User> getUserById(Integer id);

    /**
     * Get user by this email address.
     *
     * @param email User email asked.
     * @return The user object.
     */
    User getUserByEmail(String email);

    /**
     * Transfer money from or to the user's bank account.
     *
     * @param existingUser User object.
     * @param amount       amount of money user wants transfer in <i>Float</i> format.
     * @param transferType <i>String</i> indicating the type or transfer : <b>debit</b> or <b>credit</b>.
     */
    void transferFromOrToBank(User existingUser, Float amount, String transferType);

    /**
     * Change the user role.
     *
     * @param existingUser User object.
     */
    void changeUserRole(User existingUser);

    /**
     * Add a new user.
     *
     * @param newUser User object.
     * @return <i>Boolean</i> indicating if the record is done.
     */
    boolean saveUser(SignupForm newUser);

    /**
     * Delete an existing user
     *
     * @param user User object.
     */
    void userDelete(User user);

    /**
     * Change user information.
     *
     * @param existingUser User object that needs to be modified.
     * @param editedUser   User object contains new information.
     */
    void modifyUser(User existingUser, EditUserForm editedUser);

    /**
     * Add a contact to a user friend list.
     *
     * @param actualUser User object that needs to be updated
     * @param friendUser User object that needs to be added in friend list.
     */
    void addContact(User actualUser, User friendUser);

    /**
     * Remove an existing contact from a user friend list.
     *
     * @param actualUser User object that needs to be updated
     * @param friendUser User object that needs to be removed from friend list.
     */
    void removeContact(User actualUser, User friendUser);

    /**
     * Make money transaction between two users.
     *
     * @param amount          Total money in <i>Float</i> format.
     * @param user            User object that makes transaction
     * @param beneficiaryUser User object that benefits from the transaction.
     */
    void makeTransaction(float amount, User user, User beneficiaryUser);

    /**
     * Get a list of all users except those in the user friend list.
     *
     * @param user User object
     * @return a list of user no present in user friend list.
     */
    List<User> allUsersExceptFriends(User user);
}

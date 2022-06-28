package com.example.paymybuddy.service;

import com.example.paymybuddy.model.application.EditUserForm;
import com.example.paymybuddy.model.application.SignupForm;
import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class to interact with user table data.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private static final String regexpSecurity = "[\n\r\t]";

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

    @Override
    public Page<User> getUsersByPage(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
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
    public boolean saveUser(SignupForm newUser) {
        String newUserEmail = newUser.getSignupEmail();
        newUserEmail = newUserEmail.replaceAll(regexpSecurity, "_");
        User existingUser = userRepository.findByEmail(newUserEmail);

        if (existingUser != null) {
            logger.info("User no created. Email {} already exist.", newUserEmail);
            return false;
        }

        User user = new User();
        user.setEmail(newUser.getSignupEmail());
        user.setPassword(newUser.getSignupPassword());
        user.setUserName(newUser.getSignupUserName());
        user.setBankName(newUser.getSignupBankName());
        user.setRib(newUser.getSignupRib());
        user.setBalance(0.0f);
        user.setRole(false);

        userRepository.save(user);

        return true;
    }

    @Override
    public void userDelete(User existingUser) {
        userRepository.delete(existingUser);
    }

    @Override
    public void modifyUser(User existingUser, EditUserForm editedUser) {
        String userEmail = existingUser.getEmail();
        if (!editedUser.getUserName().isEmpty() && !editedUser.getUserName().equals(existingUser.getUserName())) {
            String editedUserName = editedUser.getUserName();
            editedUserName = editedUserName.replaceAll(regexpSecurity, "_");
            existingUser.setUserName(editedUserName);
            logger.info("User {} has changed is name by {}", userEmail, editedUserName);
        }
        if (!editedUser.getPassword().isEmpty() && !editedUser.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(editedUser.getPassword());
            logger.info("User {} has changed is password", userEmail);
        }
        if (!editedUser.getBankName().isEmpty() && !editedUser.getBankName().equals(existingUser.getBankName())) {
            String editedUserBank = editedUser.getBankName();
            editedUserBank = editedUserBank.replaceAll(regexpSecurity, "_");
            existingUser.setBankName(editedUserBank);
            logger.info("User {} has changed is bank name by {}", userEmail, editedUserBank);
        }
        if (!editedUser.getRib().isEmpty() && !editedUser.getRib().equals(existingUser.getRib())) {
            String editedUserRib = editedUser.getRib();
            editedUserRib = editedUserRib.replaceAll(regexpSecurity, "_");
            existingUser.setRib(editedUserRib);
            logger.info("User {} has changed is IBAN by {}", userEmail, editedUserRib);
        }

        userRepository.save(existingUser);
    }

    @Override
    public void addContact(User actualUser, User friendUser) {
        List<User> friendList = actualUser.getFriendList();
        String userEmail = actualUser.getEmail();
        String friendEmail = friendUser.getEmail();

        if (friendList == null) {
            friendList = new ArrayList<>();
        }

        if (!friendList.contains(friendUser)) {
            friendList.add(friendUser);
            actualUser.setFriendList(friendList);
            logger.info("User {} has added {} in his contacts", userEmail, friendEmail);
            userRepository.save(actualUser);
        } else {
            logger.warn("User {} already has {} in his contacts", userEmail, friendEmail);
        }
    }

    @Override
    public void removeContact(User actualUser, User friendUser) {
        List<User> friendList = actualUser.getFriendList();
        String userEmail = actualUser.getEmail();
        String friendEmail = friendUser.getEmail();


        if (friendList.contains(friendUser)) {
            friendList.remove(friendUser);
            actualUser.setFriendList(friendList);
            logger.info("User {} has removed {} from their contacts", userEmail, friendEmail);
            userRepository.save(actualUser);
        } else {
            logger.warn("User {} have not {} in his contacts", userEmail, friendEmail);
        }
    }

    @Override
    public void makeTransaction(float amount, User user, User beneficiaryUser) {

        float newBalanceUser = user.getBalance() - amount;
        user.setBalance(newBalanceUser);

        float newBalanceBeneficiary = beneficiaryUser.getBalance() + amount;
        beneficiaryUser.setBalance(newBalanceBeneficiary);

        List<User> userIterable = new ArrayList<>();
        userIterable.add(user);
        userIterable.add(beneficiaryUser);

        userRepository.saveAll(userIterable);

    }

    @Override
    public List<User> allUsersExceptFriends(User user) {
        Iterable<User> allUsers = getUsers();
        List<User> userList = new ArrayList<>();

        allUsers.forEach(userList::add);

        if (user.getFriendList() != null) {
            user.getFriendList().forEach(userList::remove);
        }

        userList.remove(user);

        return userList;
    }


}

package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.service.TransactionService;
import com.example.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Class which manage the Fees Page. In this page, administrators can see all transactions made by a user and this fees amount.
 * <p>
 * Only users with ADMIN authority can access to this page.
 */
@Controller
public class FeesPageController {
    private static final Logger logger = LogManager.getLogger(FeesPageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    /**
     * Principal method to loading Fees Page.
     *
     * @param model  Attributes needed to load the page.
     * @param userId Table ID for selected user
     * @return Fees Page.
     */
    @GetMapping("/fees")
    public String userFeesPage(Model model, @RequestParam("userId") Integer userId) {
        logger.info("searching fees information for userId {}", userId);
        Optional<User> user = userService.getUserById(userId);
        User selectedUser = new User();
        float totalFeesAmount = 0.0f;

        if (user.isPresent()) {
            selectedUser = user.get();
            logger.debug("User found with userId {} is {}", userId, selectedUser.getEmail());
            totalFeesAmount = transactionService.calculateTotalFeesAmount(selectedUser.getTransactions());
            logger.info("Total fees amount for user {} is {}€", selectedUser.getEmail(), totalFeesAmount);
        }

        model.addAttribute("user", selectedUser);
        model.addAttribute("totalFeesAmount", totalFeesAmount);
        model.addAttribute("title", "Admin");

        return "fees";
    }
}

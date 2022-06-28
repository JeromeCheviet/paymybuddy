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

@Controller
public class FeesPageController {
    private static final Logger logger = LogManager.getLogger(FeesPageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/fees")
    public String userFeesPage(Model model, @RequestParam("userId") Integer userId) {
        logger.debug("searching fees information for userId {}", userId);
        Optional<User> user = userService.getUserById(userId);
        User selectedUser = new User();
        float totalFeesAmount = 0.0f;

        if (user.isPresent()) {
            selectedUser = user.get();
            logger.debug("User found with userId {} is {}", userId, selectedUser.getEmail());
            totalFeesAmount = transactionService.calculateTotalFeesAmount(selectedUser.getTransactions());
        }

        model.addAttribute("user", selectedUser);
        model.addAttribute("totalFeesAmount", totalFeesAmount);
        model.addAttribute("title", "Admin");

        return "fees";
    }
}

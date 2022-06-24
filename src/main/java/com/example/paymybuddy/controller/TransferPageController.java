package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.AddConnectionForm;
import com.example.paymybuddy.model.application.AddTransferForm;
import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.service.TransactionService;
import com.example.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransferPageController {
    private static final Logger logger = LogManager.getLogger(TransferPageController.class);
    private Authentication auth;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transfer")
    public String transferPage(Model model) {
        logger.debug("Access transfer page");
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Iterable<User> allUsers = userService.getUsers();
        List<User> userList = new ArrayList<>();
        allUsers.forEach(tmpUser -> {
            if (!user.getFriendList().contains(tmpUser)) {
                userList.add(tmpUser);
            }
        });

        model.addAttribute("user", user);
        model.addAttribute("allUsers", userList);
        model.addAttribute("addTransferForm", new AddTransferForm());
        model.addAttribute("addConnectionForm", new AddConnectionForm());
        model.addAttribute("title", "Transfer");

        return "transfer";
    }

    @PostMapping("/sendMoney")
    public ModelAndView addTransfer(@ModelAttribute AddTransferForm addTransferForm) {
        User user = userService.getUserByEmail(auth.getName());
        ModelAndView modelAndView = new ModelAndView("redirect:/transfer");

        if (addTransferForm.getAmount() > user.getBalance()) {
            String error = "noenoughmoney";
            modelAndView.addObject("msg", error);
            return modelAndView;
        }

        transactionService.addTransaction(user, addTransferForm);

        return modelAndView;
    }

}

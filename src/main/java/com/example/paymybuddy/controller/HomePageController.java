package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.BankTransferForm;
import com.example.paymybuddy.model.dto.User;
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

/**
 * Class which manage the Home Page. It is the default page when you are logged. User can see his balance and transfer money to or from his bank account.
 */
@Controller
public class HomePageController {
    private static final Logger logger = LogManager.getLogger(HomePageController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute("bankTransferForm")
    public BankTransferForm bankTransferForm() {
        return new BankTransferForm();
    }

    /**
     * Principal method to loading Home page.
     *
     * @param model Attributes needed to load the page.
     * @return Welcome Page.
     */
    @GetMapping("/home")
    public String homePage(Model model) {
        logger.debug("Access home page");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean flag = false;
        User user = userService.getUserByEmail(auth.getName());

        if (user.getBalance() == 0) {
            flag = true;
        }
        model.addAttribute("user", user);
        model.addAttribute("title", "Home");
        model.addAttribute("flag", flag);

        return "home";
    }

    /**
     * Method to transfer money to or from user bank account.
     *
     * @param bankTransferForm Model class to receive form information.
     * @return redirect to Home page if user have enough money in this wallet or nocredit if no more money to make the transfer to his bank.
     */
    @PostMapping("/bankTransfer")
    public ModelAndView bankTransfer(@ModelAttribute("bankTransferForm") BankTransferForm bankTransferForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        logger.info("Starting a transfer to user {}", user.getEmail());
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        if (bankTransferForm.getTransferType().equals("debit")
                && bankTransferForm.getAmount() > user.getBalance()) {
            logger.info("No enough money in wallet to make this operation.");
            String error = "nocredit";
            modelAndView.addObject("msg", error);
            return modelAndView;
        }

        userService.transferFromOrToBank(user, bankTransferForm.getAmount(), bankTransferForm.getTransferType());

        return modelAndView;
    }

}

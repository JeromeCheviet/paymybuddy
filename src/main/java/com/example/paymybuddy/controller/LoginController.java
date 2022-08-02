package com.example.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class which manage the login page
 */
@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    /**
     * Manage root page.
     *
     * @param model Attributes needed to load the page.
     * @return Login page
     */
    @GetMapping("/")
    public String rootPage(Model model) {
        logger.debug("Access home page");
        return "login";
    }

    /**
     * Manage login page.
     *
     * @param model Attributes needed to load the page.
     * @return Login Page.
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        logger.debug("Access login page");
        return "login";
    }

}

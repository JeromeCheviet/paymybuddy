package com.example.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * Class which manage the login page
 */
@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    /**
     * Manage home page.
     *
     * @param model
     * @return String "login".
     */
    @GetMapping("/")
    public String homePage(Model model) {
        logger.debug("Access home page");
        return "login";
    }

    /**
     * Manage login page.
     *
     * @param model
     * @return String "login".
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        logger.debug("Access login page");
        return "login";
    }

}

package com.example.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class which manage the home page
 */
@Controller
public class HomePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    /**
     * Manage welcome page.
     *
     * @param model
     * @return String "welcome".
     */
    @GetMapping("/home")
    public String welcomePage(Model model) {
        logger.debug("Access home page");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getPrincipal());

        return "home";
    }
}

package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.SignupForm;
import com.example.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class which manage the Signup Page. Any person can create a new account.
 */
@Controller
public class SignupPageController {
    private static final Logger logger = LogManager.getLogger(SignupPageController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute("newUser")
    public SignupForm signupForm() {
        return new SignupForm();
    }

    /**
     * Principal method to loading Signup page.
     *
     * @param model Attributes needed to load the page.
     * @return Signup page.
     */
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("title", "Signup");

        return "signup";
    }

    /**
     * Method to create a new user.
     *
     * @param newUser Model class to receive form information.
     * @return If email already exist in database redirect to signup page, else redirect to login page.
     */
    @PostMapping("/newUser")
    public ModelAndView newUser(@ModelAttribute("newUser") SignupForm newUser) {
        logger.debug("Creating a new user");
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newUser.setSignupPassword(bCryptPasswordEncoder.encode(newUser.getSignupPassword()));

        Boolean isCreated = userService.saveUser(newUser);

        if (Boolean.FALSE.equals(isCreated)) {
            modelAndView = new ModelAndView("redirect:/signup");
            String msg = "mailexist";
            modelAndView.addObject("error", msg);
            logger.info("Email already exist in database");
            return modelAndView;
        }

        return modelAndView;
    }

}

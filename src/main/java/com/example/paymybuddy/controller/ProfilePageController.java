package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.EditUserForm;
import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProfilePageController {
    private static final Logger logger = LogManager.getLogger(ProfilePageController.class);

    private Authentication auth;

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public String profilePage(Model model) {
        logger.debug("Access profile page");
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        model.addAttribute("user", user);
        model.addAttribute("editedUser", new EditUserForm());
        model.addAttribute("title", "Profile");

        return "profile";
    }

    @GetMapping("/delete/{userId}")
    public ModelAndView purgeUser(@PathVariable("userId") final Integer userId) {

        Optional<User> user = userService.getUserById(userId);

        if (user.isPresent()) {
            if (user.get().getBalance() > 0) {
                ModelAndView modelAndView = new ModelAndView("redirect:/profile");
                modelAndView.addObject("msg", "balance");
                return modelAndView;
            }

            userService.userDelete(user.get());
        }

        return new ModelAndView("redirect:/logout");
    }

    @PostMapping("/editUser")
    public ModelAndView editUser(@ModelAttribute EditUserForm editedUser) {
        User connectedUser = userService.getUserByEmail(auth.getName());

        if (!editedUser.getPassword().isEmpty()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            editedUser.setPassword(bCryptPasswordEncoder.encode(editedUser.getPassword()));
        }

        userService.modifyUser(connectedUser, editedUser);

        return new ModelAndView("redirect:/profile");
    }

}

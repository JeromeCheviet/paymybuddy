package com.example.paymybuddy.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class AdminPageController {
    private static final Logger logger = LogManager.getLogger(AdminPageController.class);
    private Authentication auth;

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        Iterable<User> users = userService.getUsers();

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("title", "Admin");

        return "admin";
    }

    @GetMapping("/admin/role/{userId}")
    public ModelAndView changeRole(@PathVariable("userId") final Integer userId) {
        System.out.println(userId);

        Optional<User> user = userService.getUserById(userId);

        userService.changeUserRole(user.get());

        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/admin/delete/{userId}")
    public ModelAndView deleteUser(@PathVariable("userId") final Integer userId) {
        System.out.println(userId);
        Optional<User> user = userService.getUserById(userId);
        userService.userDelete(user.get());

        return new ModelAndView("redirect:/admin");
    }

}

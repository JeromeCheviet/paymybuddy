package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.AddConnectionForm;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ContactPageController {
    private static final Logger logger = LogManager.getLogger(ContactPageController.class);

    private Authentication auth;

    @Autowired
    private UserService userService;

    @GetMapping("/contact")
    public String contactPage(Model model) {
        logger.debug("Access contact page");
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        List<User> userList = userService.allUsersExceptFriends(user);

        model.addAttribute("user", user);
        model.addAttribute("friend", new User());
        model.addAttribute("allUsers", userList);
        model.addAttribute("eachUser", new User());
        model.addAttribute("friendUser", new User());
        model.addAttribute("addConnectionForm", new AddConnectionForm());
        model.addAttribute("title", "Contact");

        return "contact";
    }

    @PostMapping("/addConnection")
    public ModelAndView addConnection(@ModelAttribute AddConnectionForm addConnectionForm) {
        logger.debug("Add a new connection with userId : {}", addConnectionForm.getUserConnectionId());
        ModelAndView modelAndView = new ModelAndView("redirect:/contact");
        auth = SecurityContextHolder.getContext().getAuthentication();
        User actualUser = userService.getUserByEmail(auth.getName());
        if (addConnectionForm.getUserConnectionId() == 0) {
            modelAndView.addObject("msg", "noUserSelected");
            return modelAndView;
        }

        Integer userId = addConnectionForm.getUserConnectionId();

        logger.debug("Try to add user with id {} to friend from user {}", addConnectionForm.getUserConnectionId(), actualUser.getEmail());

        Optional<User> friendUser = userService.getUserById(userId);

        if (friendUser.isPresent()) {
            userService.addContact(actualUser, friendUser.get());
            logger.debug("friendUser with id {} is : {}", userId, friendUser.get().getEmail());
        }

        return modelAndView;
    }

    @GetMapping("/deleteConnection/{userId}")
    public ModelAndView deleteConnection(@PathVariable("userId") final Integer userId) {
        Optional<User> friendUser = userService.getUserById(userId);
        User actualUser = userService.getUserByEmail(auth.getName());

        if (friendUser.isPresent()) {
            userService.removeContact(actualUser, friendUser.get());
        }

        return new ModelAndView("redirect:/contact");
    }
}

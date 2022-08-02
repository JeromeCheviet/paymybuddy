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

/**
 * Class which manage the Contact Page. User can see this contacts, add a new contact or delete a connection.
 */
@Controller
public class ContactPageController {
    private static final Logger logger = LogManager.getLogger(ContactPageController.class);

    private Authentication auth;

    @Autowired
    private UserService userService;

    @ModelAttribute("addConnectionForm")
    private AddConnectionForm addConnectionForm() {
        return new AddConnectionForm();
    }

    /**
     * Principal method to loading Contact Page.
     *
     * @param model Attributes needed to load the page.
     * @return Page contact.
     */
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
        model.addAttribute("title", "Contact");

        return "contact";
    }

    /**
     * Method to add a connection with another user.
     *
     * @param addConnectionForm Model class to receive form information.
     * @return redirect to Contact page if user is selected or noUserSelected to launch an error message in contact page.
     */
    @PostMapping("/addConnection")
    public ModelAndView addConnection(@ModelAttribute("addConnectionForm") AddConnectionForm addConnectionForm) {
        logger.debug("Add a new connection with userId : {}", addConnectionForm.getUserConnectionId());
        ModelAndView modelAndView = new ModelAndView("redirect:/contact");
        auth = SecurityContextHolder.getContext().getAuthentication();
        User actualUser = userService.getUserByEmail(auth.getName());

        if (addConnectionForm.getUserConnectionId() == 0) {
            logger.info("No user have been select.");
            modelAndView.addObject("msg", "noUserSelected");
            return modelAndView;
        }

        Integer userId = addConnectionForm.getUserConnectionId();
        logger.info("Searching user with ID : {}", addConnectionForm.getUserConnectionId());
        Optional<User> friendUser = userService.getUserById(userId);

        if (friendUser.isPresent()) {
            logger.info("friendUser with id {} is : {}", userId, friendUser.get().getEmail());
            logger.info("Adding user with id {} to friend from user {}", addConnectionForm.getUserConnectionId(), actualUser.getEmail());
            userService.addContact(actualUser, friendUser.get());
        }

        return modelAndView;
    }

    /**
     * Method to remove an existing connection with another user
     *
     * @param userId Table ID for selected friend user
     * @return redirect to Contact page.
     */
    @GetMapping("/deleteConnection/{userId}")
    public ModelAndView deleteConnection(@PathVariable("userId") final Integer userId) {
        Optional<User> friendUser = userService.getUserById(userId);
        auth = SecurityContextHolder.getContext().getAuthentication();
        User actualUser = userService.getUserByEmail(auth.getName());

        if (friendUser.isPresent()) {
            logger.info("Removing user {} to friend contact of user {}", friendUser.get().getEmail(), actualUser.getEmail());
            userService.removeContact(actualUser, friendUser.get());
        }

        return new ModelAndView("redirect:/contact");
    }
}

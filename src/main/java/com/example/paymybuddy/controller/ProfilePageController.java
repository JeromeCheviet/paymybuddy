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

/**
 * Class which manage the Profile Page. User can see this information, editing it or delete own account.
 */
@Controller
public class ProfilePageController {
    private static final Logger logger = LogManager.getLogger(ProfilePageController.class);

    private Authentication auth;

    @Autowired
    private UserService userService;

    @ModelAttribute("editedUser")
    private EditUserForm editedUser() {
        return new EditUserForm();
    }

    /**
     * Principal method to loading Profile page.
     *
     * @param model Attributes needed to load the page.
     * @return Profile page.
     */
    @GetMapping("/profile")
    public String profilePage(Model model) {
        logger.debug("Access profile page");
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());

        model.addAttribute("user", user);
        model.addAttribute("title", "Profile");

        return "profile";
    }

    /**
     * Method to delete own account.
     *
     * @param userId Own user ID in database.
     * @return Log out if account is deleted or redirect to profile page if money is present in user wallet.
     */
    @GetMapping("/delete/{userId}")
    public ModelAndView purgeUser(@PathVariable("userId") final Integer userId) {
        Optional<User> user = userService.getUserById(userId);

        if (user.isPresent()) {
            float userBalance = user.get().getBalance();
            logger.debug("User balance = {}", userBalance);
            if (userBalance > 0) {
                ModelAndView modelAndView = new ModelAndView("redirect:/profile");
                modelAndView.addObject("msg", "balance");
                return modelAndView;
            }

            userService.userDelete(user.get());
        }

        return new ModelAndView("redirect:/logout");
    }

    /**
     * Method to editing profile information. Each information can be editing separately or together.
     * <br>
     * The password is encoding before treatment.
     *
     * @param editedUser Model class to receive form information.
     * @return redirect to profile page when account is updated.
     */
    @PostMapping("/editUser")
    public ModelAndView editUser(@ModelAttribute("editedUser") EditUserForm editedUser) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User connectedUser = userService.getUserByEmail(auth.getName());


        if (!editedUser.getPassword().isEmpty()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            editedUser.setPassword(bCryptPasswordEncoder.encode(editedUser.getPassword()));
        }

        logger.info("starting editing profile for user {}", connectedUser.getEmail());
        userService.modifyUser(connectedUser, editedUser);

        return new ModelAndView("redirect:/profile");
    }

}

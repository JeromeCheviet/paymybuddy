package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.SignupForm;
import com.example.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupPageController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("newUser", new SignupForm());
        model.addAttribute("title", "Signup");

        return "signup";
    }

    @PostMapping("/newUser")
    public ModelAndView newUser(@ModelAttribute SignupForm newUser) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newUser.setSignupPassword(bCryptPasswordEncoder.encode(newUser.getSignupPassword()));

        Boolean isCreated = userService.saveUser(newUser);

        if (Boolean.FALSE.equals(isCreated)) {
            modelAndView = new ModelAndView("redirect:/signup");
            String msg = "mailexist";
            modelAndView.addObject("error", msg);
            return modelAndView;
        }

        return modelAndView;
    }

}

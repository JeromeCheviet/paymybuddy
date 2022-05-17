package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.dto.User;
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
        model.addAttribute("newUser", new User());
        model.addAttribute("title", "Signup");

        return "signup";
    }

    @PostMapping("/newUser")
    public ModelAndView newUser(@ModelAttribute User newUser) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        Boolean isCreated = userService.saveUser(newUser);

        if (!isCreated) {
            modelAndView = new ModelAndView("redirect:/signup");
            String msg = "mailexist";
            modelAndView.addObject("error", msg);
            return modelAndView;
        }
        modelAndView.addObject("redirect:/");
        return modelAndView;
    }

}

package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.service.CalculateNbPage;
import com.example.paymybuddy.service.CalculateNbPageImpl;
import com.example.paymybuddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminPageController {
    private static final Logger logger = LogManager.getLogger(AdminPageController.class);

    private CalculateNbPage calculateNbPage = new CalculateNbPageImpl();

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model, @RequestParam("page") Optional<Integer> page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int currentPage = page.orElse(1);
        User user = userService.getUserByEmail(auth.getName());
        User oneUser = new User();
        Sort sort = Sort.by(Sort.Order.asc("email"));

        Page<User> users = userService.getUsersByPage(PageRequest.of(currentPage - 1, 3, sort));
        int totalPages = users.getTotalPages();
        logger.debug("They are {} page(s)", totalPages);
        List<Integer> pages = calculateNbPage.pagesList(totalPages);
        logger.debug("list pages : {}", pages.size());

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("oneUser", oneUser);
        model.addAttribute("pages", pages);
        model.addAttribute("title", "Admin");

        return "admin";
    }

    @GetMapping("/admin/role/{userId}")
    public ModelAndView changeRole(@PathVariable("userId") final Integer userId) {
        Optional<User> user = userService.getUserById(userId);

        if (user.isPresent()) {
            userService.changeUserRole(user.get());
        }

        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/admin/delete/{userId}")
    public ModelAndView deleteUser(@PathVariable("userId") final Integer userId) {
        Optional<User> user = userService.getUserById(userId);

        if (user.isPresent()) {
            userService.userDelete(user.get());
        }

        return new ModelAndView("redirect:/admin");
    }

}

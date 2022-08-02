package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.AddConnectionForm;
import com.example.paymybuddy.model.application.AddTransferForm;
import com.example.paymybuddy.model.dto.Transaction;
import com.example.paymybuddy.model.dto.User;
import com.example.paymybuddy.service.CalculateNbPage;
import com.example.paymybuddy.service.CalculateNbPageImpl;
import com.example.paymybuddy.service.TransactionService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

/**
 * Class which manage the Transfer Page. User can see transactions already made or carry out new ones.
 */
@Controller
public class TransferPageController {
    private static final Logger logger = LogManager.getLogger(TransferPageController.class);
    private Authentication auth;
    private CalculateNbPage calculateNbPage = new CalculateNbPageImpl();

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @ModelAttribute("addTransferForm")
    private AddTransferForm addTransferForm() {
        return new AddTransferForm();
    }

    /**
     * Principal method to loading Transfer page.
     *
     * @param model Attributes needed to load the page.
     * @param page  Optional number page if the tab have more 4 rows.
     * @return Transaction page.
     */
    @GetMapping("/transfer")
    public String transferPage(Model model, @RequestParam("page") Optional<Integer> page) {
        logger.debug("Access transfer page");
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        List<User> userList = userService.allUsersExceptFriends(user);

        int currentPage = page.orElse(1);
        Sort sort = Sort.by(Sort.Order.desc("date"));
        Page<Transaction> transactionPage = transactionService.getTransactionByPage(user, PageRequest.of(currentPage - 1, 3, sort));

        int totalPages = transactionPage.getTotalPages();
        logger.debug("They are {} page(s)", totalPages);
        List<Integer> pages = calculateNbPage.pagesList(totalPages);
        logger.debug("list pages : {}", pages.size());


        model.addAttribute("user", user);
        model.addAttribute("allUsers", userList);
        model.addAttribute("addConnectionForm", new AddConnectionForm());
        model.addAttribute("transactionList", transactionPage);
        model.addAttribute("pages", pages);
        model.addAttribute("title", "Transfer");

        return "transfer";
    }

    /**
     * Method to send money to an existing contact. The contact must be in the user contact list.
     *
     * @param addTransferForm Model class to receive form information.
     * @return Errors messages are sending if no contact is selected or if user have no enough money. Else redirect to Transfer page.
     */
    @PostMapping("/sendMoney")
    public ModelAndView addTransfer(@ModelAttribute("addTransferForm") AddTransferForm addTransferForm) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        ModelAndView modelAndView = new ModelAndView("redirect:/transfer");
        String error;

        if (addTransferForm.getUserId() == 0) {
            error = "noconnection";
            modelAndView.addObject("msg", error);
            logger.info("No user selected");
            return modelAndView;
        }

        if (addTransferForm.getAmount() > user.getBalance()) {
            error = "noenoughmoney";
            modelAndView.addObject("msg", error);
            logger.info("No enough money");
            return modelAndView;
        }

        transactionService.addTransaction(user, addTransferForm);

        return modelAndView;
    }

}

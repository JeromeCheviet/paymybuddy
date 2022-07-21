package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.BankTransferForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HomePageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void shouldReturnStatusOk() throws Exception {
        mvc.perform(get("/home").with(user("jerome@mail.fr").password("pass")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(username = "jerome@mail.fr")
    void testCreditBankTransfer() throws Exception {
        BankTransferForm bankTransferForm = new BankTransferForm();
        bankTransferForm.setTransferType("credit");
        bankTransferForm.setAmount(10f);

        mvc.perform(post("/bankTransfer")
                        .flashAttr("bankTransferForm", bankTransferForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/home"));

    }

    @Test
    @WithMockUser(username = "clara@mail.fr")
    void testDebitBankTransfer() throws Exception {
        BankTransferForm bankTransferForm = new BankTransferForm();
        bankTransferForm.setTransferType("debit");
        bankTransferForm.setAmount(5f);

        mvc.perform(post("/bankTransfer")
                        .flashAttr("bankTransferForm", bankTransferForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/home"));

    }

    @Test
    @WithMockUser(username = "jerome@mail.fr")
    void testDebitBankTransfer_withNoCredit() throws Exception {
        BankTransferForm bankTransferForm = new BankTransferForm();
        bankTransferForm.setTransferType("debit");
        bankTransferForm.setAmount(10f);

        mvc.perform(post("/bankTransfer")
                        .flashAttr("bankTransferForm", bankTransferForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/home?msg=nocredit"));

    }

    @Test
    void shouldReturnStatusFound_whenNoUserConnected() throws Exception {
        mvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


}

package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.AddTransferForm;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransferPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "jerome@mail.fr")
    void shouldReturnStatusOk() throws Exception {
        mvc.perform(get("/transfer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("transfer"));
    }

    @Test
    void shouldReturnStatusFound_whenNoUserConnected() throws Exception {
        mvc.perform(get("/transfer"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "jerome@mail.fr")
    void testAddTransfer_whenNoUserSelected() throws Exception {
        AddTransferForm addTransferForm = new AddTransferForm();
        addTransferForm.setUserId(0);
        addTransferForm.setAmount(2f);
        addTransferForm.setDescription("bad Connection");

        mvc.perform(post("/sendMoney")
                        .flashAttr("addTransferForm", addTransferForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer?msg=noconnection"));

    }

    @Test
    @WithMockUser(username = "jerome@mail.fr")
    void testAddTransfer_whenUserNoEnoughMoney() throws Exception {
        AddTransferForm addTransferForm = new AddTransferForm();
        addTransferForm.setUserId(2);
        addTransferForm.setAmount(100f);
        addTransferForm.setDescription("No money");

        mvc.perform(post("/sendMoney")
                        .flashAttr("addTransferForm", addTransferForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer?msg=noenoughmoney"));

    }

    @Test
    @WithMockUser(username = "clara@mail.fr")
    void testAddTransfer() throws Exception {
        AddTransferForm addTransferForm = new AddTransferForm();
        addTransferForm.setUserId(1);
        addTransferForm.setAmount(2f);
        addTransferForm.setDescription("Send 2 €");

        mvc.perform(post("/sendMoney")
                        .flashAttr("addTransferForm", addTransferForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/transfer"));

    }

}

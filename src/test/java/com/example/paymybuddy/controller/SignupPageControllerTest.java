package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.SignupForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class SignupPageControllerTest {

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
    void shouldReturnStatusOk() throws Exception {
        mvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void testNewUser() throws Exception {
        SignupForm newUser = new SignupForm();
        newUser.setSignupEmail("test@mail.fr");
        newUser.setSignupUserName("test");
        newUser.setSignupPassword("123");
        newUser.setSignupBankName("myBank");
        newUser.setSignupRib("FR076-5432-2457");

        mvc.perform(post("/newUser").flashAttr("newUser", newUser))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testNewUser_whenEmailAlreadyExist() throws Exception {
        SignupForm newUser = new SignupForm();
        newUser.setSignupEmail("jerome@mail.fr");
        newUser.setSignupUserName("test");
        newUser.setSignupPassword("123");
        newUser.setSignupBankName("myBank");
        newUser.setSignupRib("FR076-5432-2457");

        mvc.perform(post("/newUser").flashAttr("newUser", newUser))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/signup?error=mailexist"));
    }

}

package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.EditUserForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
class ProfilePageControllerTest {

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
    @Order(1)
    @WithMockUser(username = "jerome@mail.fr")
    void shouldReturnStatusOk() throws Exception {
        mvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }


    @Test
    @Order(2)
    @WithMockUser(username = "clara@mail.fr")
    void testPurgeUser_withPositiveBalance() throws Exception {
        mvc.perform(get("/delete/{userId}", 3))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/profile?msg=balance"));
    }


    @Test
    @Order(3)
    @WithMockUser(username = "hayley@mail.fr")
    void testPurgeUser() throws Exception {
        mvc.perform(get("/delete/{userId}", 2))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logout"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "smith@mail.fr")
    void testEditUser_userNameAndPassword() throws Exception {
        EditUserForm editUserForm = new EditUserForm();

        editUserForm.setPassword("8960Fjklsoc");
        editUserForm.setUserName("John");
        editUserForm.setBankName("");
        editUserForm.setRib("");

        mvc.perform(post("/editUser")
                        .flashAttr("editedUser", editUserForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/profile"));
    }

    @Test
    @Order(5)
    void shouldReturnStatusFound_whenNoUserConnected() throws Exception {
        mvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}

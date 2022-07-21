package com.example.paymybuddy.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FeesPageControllerTest {

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
    @WithMockUser(username = "jerome@mail.fr", authorities = {"ADMIN"})
    void shouldReturnStatusOk() throws Exception {
        mvc.perform(get("/fees")
                        .param("userId", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("fees"));
    }

    @Test
    @WithMockUser(username = "clara@mail.fr")
    void shouldReturnStatusForbidden_whenUserNoAuthorityAdmin() throws Exception {
        mvc.perform(get("/fees")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnFound_whenUserNoConnected() throws Exception {
        mvc.perform(get("/fees")
                        .param("userId", "2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));

    }

}

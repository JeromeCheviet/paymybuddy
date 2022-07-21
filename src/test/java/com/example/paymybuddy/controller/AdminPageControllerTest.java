package com.example.paymybuddy.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminPageControllerTest {

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
    @WithMockUser(username = "jerome@mail.fr", authorities = {"ADMIN"})
    void shouldReturnStatusOk() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "jerome@mail.fr", authorities = {"ADMIN"})
    void shouldReturnStatusOk_withParam() throws Exception {
        mvc.perform(get("/admin")
                        .param("page", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "clara@mail.fr")
    void shouldReturnStatusforbidden_whenUserNoAuthority() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(4)
    void shouldReturnStatusFound_whenUserNoConnected() throws Exception {
        mvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @Order(5)
    @WithMockUser(username = "jerome@mail.fr", authorities = {"ADMIN"})
    void testChangeRoleUserToAdmin() throws Exception {
        mvc.perform(get("/admin/role/{userId}", 2))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    @Order(6)
    @WithMockUser(username = "jerome@mail.fr", authorities = {"ADMIN"})
    void testChangeRoleAdminToUser() throws Exception {
        mvc.perform(get("/admin/role/{userId}", 2))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    @Order(7)
    @WithMockUser(username = "jerome@mail.fr", authorities = {"ADMIN"})
    void testDeleteUser() throws Exception {
        mvc.perform(get("/admin/delete/{userId}", 5))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
    }

}

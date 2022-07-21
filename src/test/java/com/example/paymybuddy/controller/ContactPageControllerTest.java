package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.application.AddConnectionForm;
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
class ContactPageControllerTest {

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
        mvc.perform(get("/contact"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("contact"));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "jerome@mail.fr")
    void testDeleteConnection_whithoutAnyFriend() throws Exception {
        mvc.perform(get("/deleteConnection/{userId}", 5))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/contact"));

    }

    @Test
    @Order(3)
    @WithMockUser(username = "jerome@mail.fr")
    void testAddConnection() throws Exception {
        AddConnectionForm addConnectionForm = new AddConnectionForm();
        addConnectionForm.setUserConnectionId(5);

        mvc.perform(post("/addConnection")
                        .flashAttr("addConnectionForm", addConnectionForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/contact"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "jerome@mail.fr")
    void testAddConnection_withAnExistingConnection() throws Exception {
        AddConnectionForm addConnectionForm = new AddConnectionForm();
        addConnectionForm.setUserConnectionId(5);

        mvc.perform(post("/addConnection")
                        .flashAttr("addConnectionForm", addConnectionForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/contact"));
    }

    @Test
    @Order(5)
    @WithMockUser(username = "jerome@mail.fr")
    void testAddConnection_withNoUserSelected() throws Exception {
        AddConnectionForm addConnectionForm = new AddConnectionForm();
        addConnectionForm.setUserConnectionId(0);

        mvc.perform(post("/addConnection")
                        .flashAttr("addConnectionForm", addConnectionForm))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/contact?msg=noUserSelected"));
    }

    @Test
    @Order(6)
    @WithMockUser(username = "jerome@mail.fr")
    void testDeleteConnection() throws Exception {
        mvc.perform(get("/deleteConnection/{userId}", 5))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/contact"));

    }

    @Test
    @Order(7)
    void shouldReturnStatusFound_whenNoUserConnected() throws Exception {
        mvc.perform(get("/contact"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}

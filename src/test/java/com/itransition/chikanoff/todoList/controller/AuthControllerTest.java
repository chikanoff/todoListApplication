package com.itransition.chikanoff.todoList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.model.dto.LoginRequest;
import com.itransition.chikanoff.todoList.model.dto.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void signInReturnsStatusOk() throws Exception {
        User user = createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername(user.getUsername());
        req.setPassword("password");

        mvc.perform(
                post("/api/auth/signin")
                   .contentType("application/json")
                   .param("sendWelcomeMail", "true")
                   .content(objectMapper.writeValueAsString(req)))
                   .andExpect(status().isOk());
    }

    @Test
    public void signInReturnsStatusUnauthorized() throws Exception {
        User user = createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername("badUsername");
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signin")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void signUpReturnsStatusOk() throws Exception {
        SignupRequest req = new SignupRequest();
        req.setFullName("testFullName");
        req.setUsername("testUsername");
        req.setEmail("email@gmail.com");
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    public void usernameExistSignUpReturnsStatusConflict() throws Exception {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail("qwe@gmail.com");
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    public void emailExistSignUpReturnsStatusConflict() throws Exception {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername("newUsername");
        req.setEmail(user.getEmail());
        req.setPassword("password");

        mvc.perform(
                        post("/api/auth/signup")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }
}

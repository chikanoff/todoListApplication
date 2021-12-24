package com.itransition.chikanoff.todoList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.beans.User;
import com.itransition.chikanoff.todoList.jwt.JwtUtils;
import com.itransition.chikanoff.todoList.payloads.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtAuthTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void existentUserGetTokenAndAuthentication() throws Exception {
        User user = createTestUser();
        LoginRequest req = new LoginRequest();
        req.setUsername(user.getUsername());
        req.setPassword("password");

        MvcResult result = mvc.perform(
                        post("/api/auth/signin")
                                .contentType("application/json")
                                .param("sendWelcomeMail", "true")
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk()).andReturn();

        String token = result.getResponse().getContentAsString()
                             .replace("{\"token\":\"", "")
                             .replace("\"}", "");

        assertThat(jwtUtils.getUserNameFromJwtToken(token)).isEqualTo(req.getUsername());
    }

}

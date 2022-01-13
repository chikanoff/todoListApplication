package com.itransition.chikanoff.todoList.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itransition.chikanoff.auth.jwt.JwtUtils;
import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.model.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtUtilsTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void validateJwtTokenReturnsTrue() throws Exception {
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
        assertThat(jwtUtils.validateJwtToken(token)).isTrue();
    }

    @Test
    public void validateJwtTokenReturnsFalse() {
        assertThat(jwtUtils.validateJwtToken("fake token")).isFalse();
    }
}

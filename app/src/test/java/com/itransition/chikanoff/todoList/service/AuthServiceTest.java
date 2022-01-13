package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.auth.jwt.JwtUtils;
import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.model.dto.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AuthServiceTest extends IntegrationTestBase {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void loginReturnsJwtResponse() {
        User user = createTestUser();
        JwtResponse response = authService.login(user.getUsername(), "password");
        assertThat(jwtUtils.getUserNameFromJwtToken(response.getToken())).isEqualTo(user.getUsername());
    }
}

package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.beans.User;
import com.itransition.chikanoff.todoList.service.jwt.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDetailsServiceTest extends IntegrationTestBase {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void loadByUsernameReturnsCorrectUsername() {
        User user = createTestUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
    }
}

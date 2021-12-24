package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.beans.User;
import com.itransition.chikanoff.todoList.service.jwt.UserDetailsImpl;
import com.itransition.chikanoff.todoList.service.jwt.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDetailsTest extends IntegrationTestBase {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Test
    public void loadByUsernameThenReturnException() {
        User user = createTestUser();
        String username = "asdasd";
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username)).hasMessage("User Not Found with username: " + username);
    }

    @Test
    public void loadByUsernameThenReturnUserDetails() {
        User user = createTestUser();

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void getUserAuthoritiesReturnNull() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.getAuthorities()).isEqualTo(null);
    }

    @Test
    public void isAccountNonExpiredReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    public void isAccountNonLockedReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isAccountNonLocked()).isTrue();
    }

    @Test
    public void isCredentialsNonExpiredReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    public void isEnabledReturnTrue() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.isEnabled()).isTrue();
    }

    @Test
    public void gettersTest() {
        User user = createTestUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertThat(userDetails.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDetails.getFullName()).isEqualTo(user.getFullName());
    }

}

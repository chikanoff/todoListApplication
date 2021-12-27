package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.model.dto.SignupRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserServiceIntegrationTest extends IntegrationTestBase {
    @Autowired
    @InjectMocks
    private UserService userService;

    @Test
    public void createUserTest() {
        SignupRequest req = new SignupRequest();
        req.setFullName("user1");
        req.setUsername("user1");
        req.setEmail("user1@gmail.com");
        req.setPassword("password");

        userService.createUser(req);

        List<User> users = getUserRepository().findAll();
        User user = users.get(users.size() - 1);
        assertThat(user.getUsername()).isEqualTo(req.getUsername());
        assertThat(user.getEmail()).isEqualTo(req.getEmail());
    }

    @Test
    public void checkEmailExistTest() {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername("username");
        req.setEmail(user.getEmail());
        req.setPassword("password");

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this email already exist");
    }

    @Test
    public void checkUsernameExistTest() {
        User user = createTestUser();
        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail("email");
        req.setPassword("password");

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this username already exist");
    }
}

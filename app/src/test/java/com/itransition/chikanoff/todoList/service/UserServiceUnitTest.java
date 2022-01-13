package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.model.dto.SignupRequest;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserReturnsEmailExist() {
        User user = User.builder().fullName("fullName")
                                  .username("username")
                                  .email("email@ia.co")
                                  .password("password")
                                  .build();
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail(user.getEmail());
        req.setPassword(user.getPassword());

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this email already exist");

    }

    @Test
    public void createUserReturnsUsernameExist() {
        User user = User.builder().fullName("fullName")
                                  .username("username")
                                  .email("email@ia.co")
                                  .password("password")
                                  .build();
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        SignupRequest req = new SignupRequest();
        req.setFullName(user.getFullName());
        req.setUsername(user.getUsername());
        req.setEmail(user.getEmail());
        req.setPassword(user.getPassword());

        assertThatThrownBy(() -> userService.createUser(req)).hasMessage("User with this username already exist");

    }
}

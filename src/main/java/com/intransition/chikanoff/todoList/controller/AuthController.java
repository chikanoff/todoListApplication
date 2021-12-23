package com.intransition.chikanoff.todoList.controller;

import com.intransition.chikanoff.todoList.payloads.request.LoginRequest;
import com.intransition.chikanoff.todoList.payloads.request.SignupRequest;
import com.intransition.chikanoff.todoList.payloads.response.JwtResponse;
import com.intransition.chikanoff.todoList.service.AuthService;
import com.intransition.chikanoff.todoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping(value = "/signup")
    public void registerUser(@RequestBody SignupRequest signUpRequest) {
        userService.createUser(signUpRequest);
    }
}

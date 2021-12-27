package com.itransition.chikanoff.todoList.controller;

import com.itransition.chikanoff.todoList.model.dto.LoginRequest;
import com.itransition.chikanoff.todoList.model.dto.SignupRequest;
import com.itransition.chikanoff.todoList.model.dto.JwtResponse;
import com.itransition.chikanoff.todoList.service.AuthService;
import com.itransition.chikanoff.todoList.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/signup")
    public void registerUser(@RequestBody SignupRequest signUpRequest) {
        userService.createUser(signUpRequest);
    }
}

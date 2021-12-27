package com.itransition.chikanoff.todoList.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 128)
    private String fullName;

    @NotBlank
    @Size(min = 5, max = 32)
    private String username;

    @NotBlank
    @Size(max = 64)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;
}

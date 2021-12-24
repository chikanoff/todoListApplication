package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.beans.User;
import com.itransition.chikanoff.todoList.exceptions.DataExistException;
import com.itransition.chikanoff.todoList.payloads.request.SignupRequest;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public void createUser(SignupRequest signupRequest) {
        checkUsernameExist(signupRequest.getUsername());
        checkEmailExist(signupRequest.getEmail());

        userRepository.saveAndFlush(new User(signupRequest.getFullName(),
                                             signupRequest.getUsername(),
                                             signupRequest.getEmail(),
                                             encoder.encode(signupRequest.getPassword())));
    }

    private void checkEmailExist(String email) throws DataExistException {
        if (userRepository.existsByEmail(email)) {
            throw new DataExistException("User with this email already exist");
        }
    }

    private void checkUsernameExist(String username) throws DataExistException {
        if (userRepository.existsByUsername(username)) {
            throw new DataExistException("User with this username already exist");
        }
    }
}

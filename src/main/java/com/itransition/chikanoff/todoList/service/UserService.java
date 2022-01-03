package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.mapper.CreateRequestTodoItemMapper;
import com.itransition.chikanoff.todoList.mapper.SignUpRequestUserMapper;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.exceptions.DataExistException;
import com.itransition.chikanoff.todoList.model.dto.SignupRequest;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public void createUser(SignupRequest signupRequest) {
        checkUsernameExist(signupRequest.getUsername());
        checkEmailExist(signupRequest.getEmail());
        User user = SignUpRequestUserMapper.INSTANCE.signUpRequestToUser(signupRequest);
        userRepository.saveAndFlush(user);
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

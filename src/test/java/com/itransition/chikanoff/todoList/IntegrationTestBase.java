package com.itransition.chikanoff.todoList;

import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.repository.TodoItemRepository;
import com.itransition.chikanoff.todoList.repository.UserRepository;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Getter
public abstract class IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User createTestUser() {
        return userRepository.saveAndFlush(
                User.builder()
                        .fullName("testFullName")
                        .username("test")
                        .email("testEmail@gmail.com")
                        .password(encoder.encode("password"))
                        .build());
    }

    public TodoItem createTestTodoItem() {
        final int year = 2021;
        final int day = 21;
        User user = createTestUser();
        TodoItem item = TodoItem.builder().name("name").description("description").date(new Date(year, Calendar.DECEMBER, day)).build();
        item.setUser(user);
        return todoItemRepository.saveAndFlush(item);
    }

    @AfterEach
    public void resetDb() {
        todoItemRepository.deleteAll();
        userRepository.deleteAll();
    }
}

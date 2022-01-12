package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.repository.TodoItemRepository;
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
public class TodoServiceUnitTest {

    @Mock
    private TodoItemRepository todoItemRepository;

    @InjectMocks
    private TodoItemServiceImpl todoItemService;

    @Test
    public void updateThrowsException() {
        Long id = 1L;
        when(todoItemRepository.findById(id).isPresent()).thenReturn(true);

        assertThatThrownBy(() -> todoItemService.update(id, new UpdateTodoItemRequest())).hasMessage("Item not found with id " + id);
    }

    @Test
    public void changeStatusThrowsException() {
        Long id = 1L;
        when(todoItemRepository.findById(id).isPresent()).thenReturn(true);

        assertThatThrownBy(() -> todoItemService.changeStatus(id)).hasMessage("Item not found with id " + id);
    }

    @Test
    public void getByIdThrowsException() {
        Long id = 1L;
        when(todoItemRepository.findById(id).isPresent()).thenReturn(true);

        assertThatThrownBy(() -> todoItemService.findById(id)).hasMessage("Item not found with id " + id);
    }
}

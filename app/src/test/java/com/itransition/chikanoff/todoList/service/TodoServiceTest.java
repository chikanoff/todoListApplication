package com.itransition.chikanoff.todoList.service;

import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.model.dto.UpdateTodoItemRequest;
import com.itransition.chikanoff.todoList.model.entity.TodoItem;
import com.itransition.chikanoff.todoList.model.entity.User;
import com.itransition.chikanoff.todoList.model.dto.CreateTodoItemRequest;
import com.itransition.chikanoff.todoList.service.jwt.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class TodoServiceTest extends IntegrationTestBase {
    private final long id = 3L;
    @Autowired
    private TodoItemServiceImpl todoItemService;

    @Test
    public void getByIdReturnsException() {
        assertThatThrownBy(() -> todoItemService.findById(id)).hasMessage("Item not found with id " + id);
    }

    @Test
    public void editReturnsException() {
        assertThatThrownBy(() -> todoItemService.update(id, new UpdateTodoItemRequest())).hasMessage("Item not found with id " + id);
    }

    @Test
    public void changeStatusReturnsException() {
        assertThatThrownBy(() -> todoItemService.changeStatus(id)).hasMessage("Item not found with id " + id);
    }

    @Test
    public void deleteReturnsException() {
        assertThatThrownBy(() -> todoItemService.delete(id)).hasMessage("Item not found with id " + id);
    }

    @Test
    public void addTodoItemTest() {
        final int year = 2021;
        final int day = 21;
        User user = createTestUser();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(UserDetailsImpl.build(user), null, null));
        CreateTodoItemRequest itemRequest = new CreateTodoItemRequest();
        itemRequest.setName("name");
        itemRequest.setDescription("desc");
        itemRequest.setDate(new Date(year, Calendar.DECEMBER, day));
        todoItemService.create(itemRequest);

        List<TodoItem> items = getTodoItemRepository().findAll();
        TodoItem item = items.get(items.size() - 1);
        assertThat(item.getName()).isEqualTo(itemRequest.getName());
        assertThat(item.getDescription()).isEqualTo(itemRequest.getDescription());
    }

    @Test
    public void deleteTodoItemTest() {
        TodoItem item = createTestTodoItem();
        assertThat(getTodoItemRepository().findAll().size()).isEqualTo(1);
        todoItemService.delete(item.getId());
        assertThat(getTodoItemRepository().findAll().size()).isEqualTo(0);
    }

    @Test
    public void changeStatusTest() {
        createTestTodoItem();
        List<TodoItem> items = getTodoItemRepository().findAll();
        TodoItem created = items.get(items.size() - 1);
        assertThat(false).isEqualTo(created.isDone());
        todoItemService.changeStatus(created.getId());
        List<TodoItem> changed = getTodoItemRepository().findAll();
        TodoItem item = changed.get(items.size() - 1);
        assertThat(item.isDone()).isTrue();
    }

    @Test
    public void editTodoItemTest() {
        TodoItem item = createTestTodoItem();
        TodoItem addedItem = getTodoItemRepository().getById(item.getId());
        assertThat(addedItem.getName()).isEqualTo(item.getName());
        assertThat(addedItem.getDescription()).isEqualTo(item.getDescription());

        TodoItem newItem = TodoItem.builder().name("newName").description("newDesc").build();
        UpdateTodoItemRequest req = new UpdateTodoItemRequest();
        req.setName(newItem.getName());
        req.setDescription(newItem.getDescription());
        req.setDate(item.getDate());
        todoItemService.update(item.getId(), req);
        TodoItem editedItem = getTodoItemRepository().getById(item.getId());
        assertThat(editedItem.getName()).isEqualTo(newItem.getName());
        assertThat(editedItem.getDescription()).isEqualTo(newItem.getDescription());
    }

    @Test
    public void getAllTest() {
        createTestTodoItem();
        assertThat(todoItemService.findAll().size()).isEqualTo(1);
    }

    @Test
    public void getOneTest() {
        TodoItem item = createTestTodoItem();
        TodoItem added = todoItemService.findById(item.getId());
        assertThat(added.getName()).isEqualTo(item.getName());
        assertThat(added.getDescription()).isEqualTo(item.getDescription());
    }
}

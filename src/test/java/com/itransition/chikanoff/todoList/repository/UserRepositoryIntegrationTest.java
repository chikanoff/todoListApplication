package com.itransition.chikanoff.todoList.repository;

import com.itransition.chikanoff.todoList.IntegrationTestBase;
import com.itransition.chikanoff.todoList.beans.User;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryIntegrationTest extends IntegrationTestBase {

    @Test
    public void findByNameReturnsUser() {
        // given
        User user = createTestUser();
        // when
        User found = getUserRepository().findByUsername(user.getUsername()).get();
        // then
        assertThat(found.getFullName()).isEqualTo(user.getFullName());
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void findByEmailReturnsUser() {
        User user = createTestUser();
        AssertionsForClassTypes.assertThat(getUserRepository().existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    public void existByUsernameReturnsTrue() {
        User user = createTestUser();

        AssertionsForClassTypes.assertThat(getUserRepository().existsByUsername(user.getUsername())).isTrue();
    }
}

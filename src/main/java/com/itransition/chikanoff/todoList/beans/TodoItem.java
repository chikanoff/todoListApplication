package com.itransition.chikanoff.todoList.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "todoItems")
@Getter
@Setter
@NoArgsConstructor
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "description", nullable = false, length = 256)
    private String description;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "isDone")
    private boolean isDone = false;

    public TodoItem(String name, String description, java.util.Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public TodoItem(String name, String description, java.util.Date date, boolean isDone) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.isDone = isDone;
    }
}

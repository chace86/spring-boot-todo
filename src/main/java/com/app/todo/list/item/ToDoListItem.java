package com.app.todo.list.item;

import com.app.todo.list.ToDoList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // required for Hibernate
@AllArgsConstructor
@Entity
@Table(name = "todo_list_items")
public class ToDoListItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank String description;
    boolean isCompleted;
    @ManyToOne
    @JoinColumn(name = "todo_list_id", nullable = false)
    ToDoList list;
}

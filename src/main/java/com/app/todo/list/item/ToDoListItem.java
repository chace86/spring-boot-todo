package com.app.todo.list.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // force = true because all fields below are private final
@AllArgsConstructor
@Entity
@Table(name = "todo_list_items")
public class ToDoListItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank String description;
    boolean isCompleted;
}

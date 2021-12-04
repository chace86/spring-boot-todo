package com.app.todo.item;

import com.app.todo.list.ToDoList;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // required for Hibernate
@AllArgsConstructor
@Entity
@Table(name = "todo_list_items")
public class ToDoListItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne @JoinColumn(name = "todo_list_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    ToDoList list;
    @NotBlank
    String description;
    boolean isCompleted;
}

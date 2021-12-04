package com.app.todo.list;

import com.app.todo.list.item.ToDoListItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // required for Hibernate
@AllArgsConstructor
@Entity
@Table(name = "todo_lists")
public class ToDoList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank String name;
    @OneToMany(mappedBy = "list") Set<ToDoListItem> items;
}

package com.app.todo.list;

import com.app.todo.list.item.ToDoListItem;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // required for Hibernate
@AllArgsConstructor
@EqualsAndHashCode(exclude = "items")
@Entity
@Table(name = "todo_lists")
public class ToDoList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank
    String username;
    @NotBlank
    String title;
    @OneToMany(mappedBy = "list")
    Set<ToDoListItem> items;
}

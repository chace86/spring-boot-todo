package com.app.todo.list;

import com.app.todo.item.ToDoListItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String username;
    @NotBlank
    String title;
    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE)
    Set<ToDoListItem> items;
}

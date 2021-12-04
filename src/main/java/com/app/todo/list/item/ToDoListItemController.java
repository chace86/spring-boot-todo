package com.app.todo.list.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/item")
public class ToDoListItemController {

    @PostMapping
    public ResponseEntity<String> saveToDoItem (@Valid @RequestBody ToDoListItem item) {
        // TODO: add service

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(item.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<String> updateToDoItem (@Valid @RequestBody ToDoListItem item) {
        // todo: add service
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ToDoListItem findToDoListItem(@PathVariable long id) {
        // TODO: add service
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteToDoListItem(@PathVariable long id) {
        // TODO: add service
        return ResponseEntity.noContent().build();
    }
}

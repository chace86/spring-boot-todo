package com.app.todo.list;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ToDoListController {

    @PostMapping("/list")
    public ResponseEntity<String> addToDoList(@Valid @RequestBody ToDoList list) {
        // TODO: Save todo list here

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(list.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/list")
    public ResponseEntity<String> updateToDoList(@PathVariable int id, @RequestParam String name) {
        // TODO: update here

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public Set<ToDoList> findAllToLists() {
        // TODO: find all todo lists here

        return new HashSet<>();
    }

    @DeleteMapping("/list")
    public ResponseEntity<String> deleteToDoList(@PathVariable int id) {
        // TODO:  delete todo here

        return ResponseEntity.noContent().build();
    }
}

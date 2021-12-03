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

    private final ToDoListService service;

    public ToDoListController(ToDoListService service) {
        this.service = service;
    }

    @PostMapping("/list")
    public ResponseEntity<String> addToDoList(@Valid @RequestBody ToDoList list) {
        service.addToDoList(list);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(list.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/list")
    public ResponseEntity<String> updateToDoList(@PathVariable long id, @RequestParam String name) {
        service.updateToDoList(id, name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public Set<ToDoList> findAllToLists() {
        return service.findAllToDoLists();
    }

    @DeleteMapping("/list")
    public ResponseEntity<String> deleteToDoList(@PathVariable long id) {
        service.deleteToDoList(id);
        return ResponseEntity.noContent().build();
    }
}

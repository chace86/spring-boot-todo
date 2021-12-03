package com.app.todo.list;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ToDoListController {

    private final ToDoListService service;

    public ToDoListController(ToDoListService service) {
        this.service = service;
    }

    @PostMapping("/list")
    public ResponseEntity<String> saveToDoList(@Valid @RequestBody ToDoList list) {
        boolean isCreated = service.saveToDoList(list);

        if (isCreated) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(list.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/list")
    public ResponseEntity<String> updateToDoList(ToDoList list) {
        service.updateToDoList(list);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public List<ToDoList> findAllToLists() {
        return service.findAllToDoLists();
    }

    @DeleteMapping("/list")
    public ResponseEntity<String> deleteToDoList(@PathVariable long id) {
        service.deleteToDoList(id);
        return ResponseEntity.noContent().build();
    }
}

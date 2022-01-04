package ru.ndg.myapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ndg.myapp.model.ToDo;
import ru.ndg.myapp.service.ToDoService;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService service;

    @GetMapping
    public List<ToDo> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ToDo findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public String save(@RequestBody ToDo toDo) {
        return service.save(toDo).getId();
    }

    @PutMapping
    public String update(@RequestBody ToDo toDo) {
        return service.update(toDo).getId();
    }

    @PatchMapping("/{id}")
    public ToDo setDone(@PathVariable String id) {
        return service.setDone(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return service.delete(id);
    }
}

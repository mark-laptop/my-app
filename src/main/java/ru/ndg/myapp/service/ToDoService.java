package ru.ndg.myapp.service;

import ru.ndg.myapp.model.ToDo;

import java.util.List;

public interface ToDoService {
    List<ToDo> findAll();
    ToDo findById(String id);
    ToDo save(ToDo toDo);
    String delete(String id);
    ToDo setDone(String id);
    ToDo update(ToDo toDo);
}

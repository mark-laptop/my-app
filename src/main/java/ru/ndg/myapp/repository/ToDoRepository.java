package ru.ndg.myapp.repository;

import ru.ndg.myapp.model.ToDo;

import java.util.List;

public interface ToDoRepository {
    ToDo save(ToDo toDo);
    List<ToDo> findAll();
    String delete(String id);
    void setDone(String id);
    ToDo findById(String id);
    ToDo update(ToDo toDo);
}

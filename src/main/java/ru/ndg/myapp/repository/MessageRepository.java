package ru.ndg.myapp.repository;

import ru.ndg.myapp.model.KafkaToDo;

public interface MessageRepository {
    void save(KafkaToDo toDo);
}

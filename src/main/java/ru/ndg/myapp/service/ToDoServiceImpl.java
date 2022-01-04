package ru.ndg.myapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.myapp.model.ToDo;
import ru.ndg.myapp.producer.KafkaProducer;
import ru.ndg.myapp.repository.ToDoRepository;

import java.util.List;

import static ru.ndg.myapp.util.Operation.*;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repository;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<ToDo> findAll() {
        return repository.findAll();
    }

    @Override
    public ToDo findById(String id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public ToDo save(ToDo toDo) {
        var toDoExist = repository.save(toDo);
        kafkaProducer.send(toDoExist, CREATE);
        return toDoExist;
    }

    @Override
    @Transactional
    public ToDo update(ToDo toDo) {
        var toDoExist = repository.update(toDo);
        kafkaProducer.send(toDoExist, UPDATE);
        return toDoExist;
    }

    @Override
    @Transactional
    public ToDo setDone(String id) {
        repository.setDone(id);
        var todo = repository.findById(id);
        kafkaProducer.send(todo, UPDATE);
        return todo;
    }

    @Override
    @Transactional
    public String delete(String id) {
        var todo = repository.findById(id);
        kafkaProducer.send(todo, DELETE);
        return repository.delete(id);
    }
}

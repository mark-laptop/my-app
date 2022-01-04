package ru.ndg.myapp.util;

import org.springframework.stereotype.Component;
import ru.ndg.myapp.model.KafkaToDo;
import ru.ndg.myapp.model.ToDo;

import java.time.LocalDateTime;

@Component
public class KafkaDtoHelper {

    public KafkaToDo toKafkaDto(ToDo todo, Operation operation) {
        return KafkaToDo.builder()
                .messageId(todo.getId())
                .operation(operation.getCode())
                .sendDate(LocalDateTime.now())
                .description(todo.getDescription())
                .created(todo.getCreated())
                .modified(todo.getModified())
                .done(todo.isDone())
                .build();
    }
}

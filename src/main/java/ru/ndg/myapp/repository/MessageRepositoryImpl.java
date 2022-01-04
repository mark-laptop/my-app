package ru.ndg.myapp.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ndg.myapp.model.KafkaToDo;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

    private static final String TABLE = "kafka";
    private static final String OPERATION = "operation";
    private static final String SEND_DATE = "send_date";
    private static final String MESSAGE_ID = "message_id";
    private static final String DESCRIPTION = "description";
    private static final String CREATED = "created";
    private static final String MODIFIED = "modified";
    private static final String DONE = "done";

    private static final String INSERT = "insert into " + TABLE + " (operation, send_date, message_id, description, created, modified, done) values (:operation, :send_date, :message_id, :description, :created, :modified, :done)";

    private final NamedParameterJdbcTemplate template;

    @Override
    public void save(KafkaToDo toDo) {
        template.update(INSERT, Map.of(
                OPERATION, toDo.getOperation(),
                SEND_DATE, toDo.getSendDate(),
                MESSAGE_ID, toDo.getMessageId(),
                DESCRIPTION, toDo.getDescription(),
                CREATED, toDo.getCreated(),
                MODIFIED, toDo.getModified(),
                DONE, toDo.isDone()
        ));
    }
}

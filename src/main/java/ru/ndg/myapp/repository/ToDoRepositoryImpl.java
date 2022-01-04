package ru.ndg.myapp.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ndg.myapp.model.ToDo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ToDoRepositoryImpl implements ToDoRepository {

    private static final String TABLE = "to_do";
    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String CREATED = "created";
    private static final String MODIFIED = "modified";
    private static final String DONE = "done";

    private static final String INSERT = "insert into " + TABLE + " (id, description, created, modified, done) values (:id, :description, :created, :modified, :done)";
    private static final String DELETE = "delete from " + TABLE;
    private static final String SELECT = "select * from " + TABLE;
    private static final String SET_DONE = "update " + TABLE + " set done = :done";
    private static final String UPDATE = "update " + TABLE + " set description = :description, modified = :modified, done = :done";
    private static final String WHERE_ID = " where id = :id";

    private static final ResultSetExtractor<ToDo> RS_EXTRACTOR = rs -> {
        rs.next();
        return ToDo.builder()
                .id(rs.getString(ID))
                .description(rs.getString(DESCRIPTION))
                .created(rs.getTimestamp(CREATED).toLocalDateTime())
                .modified(rs.getTimestamp(MODIFIED).toLocalDateTime())
                .done(rs.getBoolean(DONE))
                .build();
    };
    private static final RowMapper<ToDo> ROW_MAPPER = (rs, rowNum) ->
            ToDo.builder()
                    .id(rs.getString(ID))
                    .description(rs.getString(DESCRIPTION))
                    .created(rs.getTimestamp(CREATED).toLocalDateTime())
                    .modified(rs.getTimestamp(MODIFIED).toLocalDateTime())
                    .done(rs.getBoolean(DONE))
                    .build();

    private final NamedParameterJdbcTemplate template;

    @Override
    public ToDo save(ToDo toDo) {
        var id = UUID.randomUUID().toString();
        var date = LocalDateTime.now();
        template.update(INSERT, Map.of(
                ID, id,
                DESCRIPTION, toDo.getDescription(),
                CREATED, date,
                MODIFIED, date,
                DONE, false
        ));
        return findById(id);
    }

    @Override
    public ToDo update(ToDo toDo) {
        template.update(UPDATE + WHERE_ID, Map.of(
                ID, toDo.getId(),
                DESCRIPTION, toDo.getDescription(),
                MODIFIED, LocalDateTime.now(),
                DONE, toDo.isDone()
        ));
        return findById(toDo.getId());
    }

    @Override
    public void setDone(String id) {
        template.update(SET_DONE + WHERE_ID, Map.of(ID, id, DONE, true));
    }

    @Override
    public List<ToDo> findAll() {
        return template.query(SELECT, ROW_MAPPER);
    }

    @Override
    public ToDo findById(String id) {
        return template.query(SELECT + WHERE_ID, Map.of(ID, id), RS_EXTRACTOR);
    }

    @Override
    public String delete(String id) {
        template.update(DELETE + WHERE_ID, Map.of(ID, id));
        return id;
    }
}

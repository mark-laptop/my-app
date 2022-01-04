package ru.ndg.myapp.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.ndg.myapp.exception.KafkaDtoParsingException;
import ru.ndg.myapp.model.ToDo;
import ru.ndg.myapp.util.KafkaDtoHelper;
import ru.ndg.myapp.util.Operation;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    @Value("${kafka.data-topic}")
    private String dataTopicName;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaDtoHelper helper;

    public void send(ToDo todo, Operation operation) {
        try {
            var kafkaToDo = helper.toKafkaDto(todo, operation);
            var message = objectMapper.writeValueAsString(kafkaToDo);
            kafkaTemplate.send(dataTopicName, message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new KafkaDtoParsingException(e);
        }
    }
}

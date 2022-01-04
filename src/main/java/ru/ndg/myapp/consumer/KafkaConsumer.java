package ru.ndg.myapp.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.ndg.myapp.exception.KafkaDtoParsingException;
import ru.ndg.myapp.model.KafkaToDo;
import ru.ndg.myapp.repository.MessageRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final MessageRepository repository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.data-topic}", concurrency = "2")
    public void messageConsumer(String message) {
        try {
            var toDo = objectMapper.readValue(message, KafkaToDo.class);
            repository.save(toDo);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new KafkaDtoParsingException(e);
        }
    }
}

package ru.ndg.myapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        var javaTime =  new JavaTimeModule();
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTime);
        return objectMapper;
    }
}

package ru.ndg.myapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {
    private String id;
    private String description;
    @JsonFormat(pattern = "yyyy MM dd HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(pattern = "yyyy MM dd HH:mm:ss")
    private LocalDateTime modified;
    private boolean done;
}

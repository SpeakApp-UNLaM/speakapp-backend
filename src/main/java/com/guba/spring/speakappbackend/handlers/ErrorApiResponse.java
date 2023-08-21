package com.guba.spring.speakappbackend.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorApiResponse {

    private final int status;
    private final LocalDateTime timestamp;
    private final List<String> errors;
}

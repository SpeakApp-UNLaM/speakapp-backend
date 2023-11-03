package com.guba.spring.speakappbackend.web.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatContactDTO {
    private AuthorDTO author;
    private String lastMessage;
    private LocalDateTime lastDateMessage;
}

package com.guba.spring.speakappbackend.schemas.chatgpt;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Message implements Serializable {
    private String role;
    private String content;
}

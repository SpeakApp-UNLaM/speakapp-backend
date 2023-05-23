package com.guba.spring.speakappbackend.schemas.chatgpt;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatRequest implements Serializable {
    private String question;
}

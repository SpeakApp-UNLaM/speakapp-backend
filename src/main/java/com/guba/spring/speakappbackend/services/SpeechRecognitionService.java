package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.clients.OpenAIClient;
import com.guba.spring.speakappbackend.clients.WhisperTranscriptionRequest;
import com.guba.spring.speakappbackend.clients.WhisperTranscriptionResponse;
import com.guba.spring.speakappbackend.configs.OpenAIConfig;
import com.guba.spring.speakappbackend.schemas.*;
import com.guba.spring.speakappbackend.schemas.chatgpt.ChatGPTRequest;
import com.guba.spring.speakappbackend.schemas.chatgpt.ChatGPTResponse;
import com.guba.spring.speakappbackend.schemas.chatgpt.ChatRequest;
import com.guba.spring.speakappbackend.schemas.chatgpt.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SpeechRecognitionService {

    private final OpenAIClient openAIClient;
    private final OpenAIConfig openAIConfig;

    private final static String ROLE_USER = "user";

    public ChatGPTResponse chat(ChatRequest chatRequest){
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(chatRequest.getQuestion())
                .build();
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIConfig.getGptModel())
                .messages(Collections.singletonList(message))
                .build();
        return openAIClient.chat(chatGPTRequest);
    }

    public WhisperTranscriptionResponse getTranscription(TranscriptionRequest transcriptionRequest){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model(openAIConfig.getAudioModel())
                .file(transcriptionRequest.getFile())
                .language("es")
                .temperature(0)
                .build();
        return openAIClient.createTranscription(whisperTranscriptionRequest);
    }
}
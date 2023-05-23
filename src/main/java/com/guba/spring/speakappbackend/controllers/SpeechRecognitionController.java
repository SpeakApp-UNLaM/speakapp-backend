package com.guba.spring.speakappbackend.controllers;

import com.guba.spring.speakappbackend.clients.WhisperTranscriptionResponse;
import com.guba.spring.speakappbackend.schemas.TranscriptionRequest;
import com.guba.spring.speakappbackend.schemas.chatgpt.ChatGPTResponse;
import com.guba.spring.speakappbackend.schemas.chatgpt.ChatRequest;
import com.guba.spring.speakappbackend.services.SpeechRecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "speech-recognition")
public class SpeechRecognitionController {

    private final SpeechRecognitionService openAIService;

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatGPTResponse chat(@RequestBody ChatRequest chatRequest){
        return openAIService.chat(chatRequest);
    }

    @PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WhisperTranscriptionResponse getTranscription(@ModelAttribute TranscriptionRequest transcriptionRequest){
        return openAIService.getTranscription(transcriptionRequest);
    }
}
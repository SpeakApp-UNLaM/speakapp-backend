package com.guba.spring.speakappbackend.clients;


import com.guba.spring.speakappbackend.configs.OpenAIConfig;
import com.guba.spring.speakappbackend.schemas.chatgpt.ChatGPTRequest;
import com.guba.spring.speakappbackend.schemas.chatgpt.ChatGPTResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "openai-service",
        url = "${speak-backend.client.openai.urls.base-url}",
        configuration = OpenAIConfig.class
)
public interface OpenAIClient {

    @PostMapping(value = "${speak-backend.client.openai.urls.chat-url}", headers = {"Content-Type=application/json"})
    ChatGPTResponse chat(@RequestBody ChatGPTRequest chatGPTRequest);

    @PostMapping(value = "${speak-backend.client.openai.urls.create-transcription-url}", headers = {"Content-Type=multipart/form-data"})
    WhisperTranscriptionResponse createTranscription(@ModelAttribute WhisperTranscriptionRequest whisperTranscriptionRequest);
}
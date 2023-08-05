package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.clients.WhisperTranscriptionRequest;
import com.guba.spring.speakappbackend.schemas.MultipartFileDTO;
import com.guba.spring.speakappbackend.schemas.TranscriptionResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpeechRecognitionService {

    private final ClientWhisperApiCustom clientWhisperApiCustom;

    public TranscriptionResultDTO getTranscription(MultipartFileDTO multipartFileDTO){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model("whisper-1")
                .file(multipartFileDTO.getFile())
                .language("es")
                .temperature(0)
                .build();
        var whisperTranscriptionResponse = clientWhisperApiCustom.getTranscription(whisperTranscriptionRequest);
        return TranscriptionResultDTO
                .builder()
                .text(whisperTranscriptionResponse.getText())
                .build();
    }
}
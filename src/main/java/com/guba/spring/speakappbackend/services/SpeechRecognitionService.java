package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.web.schemas.MultipartFileDTO;
import com.guba.spring.speakappbackend.web.schemas.TranscriptionResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpeechRecognitionService {

    private final ClientWhisperApiCustom clientWhisperApiCustom;

    public TranscriptionResultDTO getTranscription(MultipartFileDTO multipartFileDTO){
        var whisperTranscriptionResponse = clientWhisperApiCustom.getTranscription(multipartFileDTO.getFile().getResource());
        return TranscriptionResultDTO
                .builder()
                .text(whisperTranscriptionResponse.getText())
                .build();
    }
}
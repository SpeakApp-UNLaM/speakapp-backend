package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.clients.ModeSpeak;
import com.guba.spring.speakappbackend.web.schemas.MultipartFileDTO;
import com.guba.spring.speakappbackend.web.schemas.TranscriptionResultDTO;
import com.guba.spring.speakappbackend.services.SpeechRecognitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "speech-recognition")
@Slf4j
public class SpeechRecognitionController {

    private final SpeechRecognitionService openAIService;

    @PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TranscriptionResultDTO getTranscription(
            @ModelAttribute MultipartFileDTO multipartFileDTO,
            @RequestParam(required = false, defaultValue = "SLOW") ModeSpeak mode
    ) throws IOException {
        log.info("Reconocimiento de voz en proceso....");
        Base64
                .getEncoder()
                .encodeToString(multipartFileDTO.getFile().getBytes());
        return openAIService.getTranscription(multipartFileDTO, mode);
    }
}
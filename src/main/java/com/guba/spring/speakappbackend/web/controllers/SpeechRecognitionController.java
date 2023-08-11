package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.web.schemas.MultipartFileDTO;
import com.guba.spring.speakappbackend.web.schemas.TranscriptionResultDTO;
import com.guba.spring.speakappbackend.services.SpeechRecognitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "speech-recognition")
@Slf4j
public class SpeechRecognitionController {

    private final SpeechRecognitionService openAIService;

    @PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TranscriptionResultDTO getTranscription(@ModelAttribute MultipartFileDTO multipartFileDTO){
        log.info("Reconocimiento de voz en proceso....");
        return openAIService.getTranscription(multipartFileDTO);
    }
}
package com.guba.spring.speakappbackend.clients;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhisperTranscriptionRequest {

    private String model;
    private MultipartFile file;
    private String language;
    //[0-1]
    private float temperature;
}

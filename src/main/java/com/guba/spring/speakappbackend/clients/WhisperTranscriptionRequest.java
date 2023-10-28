package com.guba.spring.speakappbackend.clients;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhisperTranscriptionRequest {

    private String model;
    private Resource resourceData;
    private String language;
    //[0-1]
    private float temperature;
}

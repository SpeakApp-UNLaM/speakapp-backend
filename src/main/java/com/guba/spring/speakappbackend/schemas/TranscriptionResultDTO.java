package com.guba.spring.speakappbackend.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranscriptionResultDTO {
    private String text;
}

package com.guba.spring.speakappbackend.web.schemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterPhonologicalInducedDTO {
    private Long idRfi;
    private String imageData;
    private String name;
    private String status;
}

package com.guba.spring.speakappbackend.web.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long idPatient;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String imageData;
    private ProfessionalDTO professional;
}

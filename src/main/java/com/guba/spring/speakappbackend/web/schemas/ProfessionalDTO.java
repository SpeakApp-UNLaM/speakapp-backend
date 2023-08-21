package com.guba.spring.speakappbackend.web.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalDTO {

    private Long idProfessional;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String code;
    private String imageData;
    private String gender;
    private Set<PatientDTO> patients;
}

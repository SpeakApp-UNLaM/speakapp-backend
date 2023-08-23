package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.database.models.Patient;
import com.guba.spring.speakappbackend.database.models.Professional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

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
    private String codeProfessional;
    private String imageData;

    public static PatientDTO create(Patient p) {
        final String code = Optional
                .ofNullable(p.getProfessional())
                .map(Professional::getCode)
                .orElse(null);
        return PatientDTO
                .builder()
                .idPatient(p.getIdPatient())
                .email(p.getEmail())
                .username(p.getUsername())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .age(p.getAge())
                .gender(p.getGender())
                .imageData(p.getImageData())
                .codeProfessional(code)
                .build();
    }
}

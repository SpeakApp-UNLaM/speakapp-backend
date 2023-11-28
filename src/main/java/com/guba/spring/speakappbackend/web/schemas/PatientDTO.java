package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.storages.database.models.Patient;
import com.guba.spring.speakappbackend.storages.database.models.Professional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private String tutor;
    private String phone;
    private String codeProfessional;
    private String imageData;
    private LocalDateTime createAt;

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
                .phone(p.getPhone())
                .tutor(p.getTutor())
                .createAt(p.getCreatedAt())
                .imageData(p.getImageData())
                .codeProfessional(code)
                .build();
    }
}

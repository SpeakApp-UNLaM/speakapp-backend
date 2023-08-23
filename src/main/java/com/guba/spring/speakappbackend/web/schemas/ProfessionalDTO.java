package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.database.models.Professional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

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

    public static ProfessionalDTO create(Professional p) {
        return ProfessionalDTO
                .builder()
                .idProfessional(p.getIdProfessional())
                .firstName(p.getFirstName())
                .lastName(p.getLastName())
                .age(p.getAge())
                .code(p.getCode())
                .email(p.getEmail())
                .username(p.getUsername())
                .gender(p.getGender())
                .imageData(p.getImageData())
                .patients(
                        p.getPatients()
                                .stream()
                                .map(PatientDTO::create)
                                .collect(Collectors.toSet()))
                .build();
    }
}

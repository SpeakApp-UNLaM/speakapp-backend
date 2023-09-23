package com.guba.spring.speakappbackend.database.models;

import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import com.guba.spring.speakappbackend.web.schemas.PatientDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tm_patient")
@Setter
@Getter
@NoArgsConstructor
public class Patient extends UserAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long idPatient;

    @ManyToOne
    @JoinColumn(name="id_professional", nullable=false)
    private Professional professional;

    public Patient(PatientDTO p, String password, Role role, LocalDateTime createdAt, LocalDateTime updateAt, Professional professional) {
        super();
        this.setIdPatient(p.getIdPatient());
        this.setUsername(p.getUsername());
        this.setPassword(password);
        this.setEmail(p.getEmail());
        this.setFirstName(p.getFirstName());
        this.setLastName(p.getLastName());
        this.setAge(p.getAge());
        this.setGender(p.getGender());
        this.setImageData(p.getImageData());
        this.setRole(role);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updateAt);
        this.setProfessional(professional);
    }

    public Patient(SignUpDTO p, String password, Role role, LocalDateTime createdAt, LocalDateTime updateAt, Professional professional) {
        super();
        this.setUsername(p.getUsername());
        this.setPassword(password);
        this.setEmail(p.getEmail());
        this.setFirstName(p.getFirstName());
        this.setLastName(p.getLastName());
        this.setRole(role);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updateAt);
        this.setProfessional(professional);
    }
}

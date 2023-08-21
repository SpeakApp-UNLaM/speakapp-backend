package com.guba.spring.speakappbackend.database.models;

import com.guba.spring.speakappbackend.web.schemas.PatientDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    public Patient(PatientDTO patientDTO) {
        super();
    }
}

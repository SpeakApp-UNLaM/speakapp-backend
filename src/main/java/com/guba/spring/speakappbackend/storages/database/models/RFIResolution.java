package com.guba.spring.speakappbackend.storages.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_rfi_resolution")
@Setter
@Getter
@NoArgsConstructor
public class RFIResolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rfi_resolution")
    private Long idRfiResolved;

    @Column(name = "id_rfi")
    private Long idRfi;

    @Column(name = "id_patient")
    private Long idPatient;

    @Column(name = "status_resolution")
    private String statusResolution;

    @OneToOne
    @JoinColumn(name = "id_rfi", referencedColumnName = "id_rfi", insertable=false, updatable = false)
    private RegisterPhonologicalInduced rfi;

    public RFIResolution(Long idRfi, Long idPatient, String statusResolution) {
        this.idRfi = idRfi;
        this.idPatient = idPatient;
        this.statusResolution = statusResolution;
    }
}

package com.guba.spring.speakappbackend.database.models;

import com.guba.spring.speakappbackend.web.schemas.ProfessionalDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tm_professional")
@Setter
@Getter
@NoArgsConstructor
public class Professional extends UserAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professional")
    private Long idProfessional;

    @ManyToOne
    @JoinColumn(name="id_role", nullable=false)
    private Role role;

    @Column(name = "code")
    private String code;

    @OneToMany
    @JoinColumn(name = "id_patient")
    private Set<Patient> patients;

    public Professional(ProfessionalDTO professionalDTO) {
        super();
        this.setFirstName(professionalDTO.getFirstName());
        this.setLastName(professionalDTO.getLastName());
        this.setAge(professionalDTO.getAge());
    }
}

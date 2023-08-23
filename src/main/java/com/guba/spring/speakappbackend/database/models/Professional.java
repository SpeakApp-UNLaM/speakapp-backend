package com.guba.spring.speakappbackend.database.models;

import com.guba.spring.speakappbackend.security.dtos.SignUpDTO;
import com.guba.spring.speakappbackend.web.schemas.ProfessionalDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy="professional")
    private Set<Patient> patients = new HashSet<>();

    public Professional(ProfessionalDTO p, String password, Role role, LocalDateTime createdAt, LocalDateTime updateAt, Set<Patient> patients) {
        super();
        this.setIdProfessional(p.getIdProfessional());
        this.setUsername(p.getUsername());
        this.setPassword(password);
        this.setCode(p.getCode());
        this.setEmail(p.getEmail());
        this.setFirstName(p.getFirstName());
        this.setLastName(p.getLastName());
        this.setAge(p.getAge());
        this.setGender(p.getGender());
        this.setImageData(p.getImageData());
        this.setRole(role);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updateAt);
        this.setPatients(patients);
    }

    public Professional(SignUpDTO p, String password, String code, Role role, LocalDateTime createdAt, LocalDateTime updateAt) {
        super();
        this.setUsername(p.getUsername());
        this.setCode(code);
        this.setPassword(password);
        this.setEmail(p.getEmail());
        this.setFirstName(p.getFirstName());
        this.setLastName(p.getLastName());
        this.setRole(role);
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updateAt);
    }
}

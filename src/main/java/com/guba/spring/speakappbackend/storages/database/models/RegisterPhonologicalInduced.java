package com.guba.spring.speakappbackend.storages.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_rfi")
@Setter
@Getter
@NoArgsConstructor
public class RegisterPhonologicalInduced {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rfi")
    private Long idRfi;

    @OneToOne
    @JoinColumn(name = "id_image", referencedColumnName = "id_image")
    private Image image;
}

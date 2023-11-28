package com.guba.spring.speakappbackend.storages.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_articulation")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Articulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulation")
    private Long idArticulation;

    @Column(name = "id_phoneme")
    private Long idPhoneme;

    @ManyToOne
    @JoinColumn(name="id_phoneme", nullable = false, insertable = false, updatable = false)
    private Phoneme phoneme;

    @Column(name = "image_data")
    private String imageData;

    public Articulation(Long idArticulation, Long idPhoneme, String imageData) {
        this.idArticulation = idArticulation;
        this.idPhoneme = idPhoneme;
        this.imageData = imageData;
    }
}

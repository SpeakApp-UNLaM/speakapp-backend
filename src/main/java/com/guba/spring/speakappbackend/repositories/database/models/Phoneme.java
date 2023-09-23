package com.guba.spring.speakappbackend.repositories.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_phoneme")
@Setter
@Getter
@NoArgsConstructor
public class Phoneme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phoneme")
    private Long idPhoneme;

    @Column(name = "phoneme")
    private String namePhoneme;
}

package com.guba.spring.speakappbackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_patient")
@Setter
@Getter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long idPatient;

    @ManyToOne
    @JoinColumn(name="id_professional", nullable=false)
    private Professional professional;

    @ManyToOne
    @JoinColumn(name="id_role", nullable=false)
    private Role role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

}

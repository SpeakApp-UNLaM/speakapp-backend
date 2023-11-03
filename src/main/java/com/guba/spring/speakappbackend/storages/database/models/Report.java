package com.guba.spring.speakappbackend.storages.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tm_report")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_professional")
    private Professional professional;

    @ManyToOne
    @JoinColumn(name="id_patient")
    private Patient patient;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    public Report(Professional professional, Patient patient, LocalDateTime createdAt, String title, String body) {
        this.professional = professional;
        this.patient = patient;
        this.createdAt = createdAt;
        this.title = title;
        this.body = body;
    }
}

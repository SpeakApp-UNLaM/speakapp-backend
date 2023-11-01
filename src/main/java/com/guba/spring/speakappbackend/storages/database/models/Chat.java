package com.guba.spring.speakappbackend.storages.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tm_chat")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_professional")
    private Professional professional;

    @ManyToOne
    @JoinColumn(name="id_patient")
    private Patient patient;

    @OneToMany(mappedBy="chat")
    private Set<ChatMessage> chatMessages = new HashSet<>();

    public Chat(Patient patient, Professional professional) {
        this.patient = patient;
        this.professional = professional;
        this.chatMessages = new HashSet<>();
    }
}

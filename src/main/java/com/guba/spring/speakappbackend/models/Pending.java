package com.guba.spring.speakappbackend.models;

import com.guba.spring.speakappbackend.schemas.CareerDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pending_exercises")
@Setter
@Getter
@NoArgsConstructor
public class Pending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_usr")
    private int idUsr;

    @Column(name = "id_exercise")
    private int idExercise;

    @Column(name = "id_group_exercise")
    private int idGroupExercise;
    public Pending(PendingDTO c) {
        this.idUsr = c.getIdUsr();
        this.idExercise = c.getIdExercise();
        this.idGroupExercise = c.getIdGroupExercise();

    }
}
package com.guba.spring.speakappbackend.models;

import com.guba.spring.speakappbackend.schemas.GroupExerciseDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_group_exercise")
@Setter
@Getter
@NoArgsConstructor
public class GroupExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "word_group_exercise")
    private String wordGroupExercise;
    public GroupExercise(GroupExerciseDTO c) {
        this.id = c.getId();
        this.wordGroupExercise = c.getWordGroupExercise();

    }
}
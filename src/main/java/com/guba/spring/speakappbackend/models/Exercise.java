package com.guba.spring.speakappbackend.models;

import com.guba.spring.speakappbackend.schemas.ExerciseDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_exercise")
@Setter
@Getter
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "word_exercise")
    private String wordExercise;

    @Column(name = "id_group_exercise")
    private int idGroupExercise;

    @Column(name = "url_image_related")
    private String urlImageRelated;
    @Column(name = "result_expected")
    private String resultExpected;
    @Column(name = "level")
    private int level;
    public Exercise(ExerciseDTO c) {
        this.id = c.getId();
        this.wordExercise = c.getWordExercise();
        this.idGroupExercise = c.getIdGroupExercise();
        this.urlImageRelated = c.getUrlImageRelated();
        this.resultExpected = c.getResultExpected();
        this.level = c.getLevel();

    }
}
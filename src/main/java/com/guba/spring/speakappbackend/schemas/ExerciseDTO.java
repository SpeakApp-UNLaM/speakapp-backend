package com.guba.spring.speakappbackend.schemas;

import com.guba.spring.speakappbackend.models.Exercise;
import com.guba.spring.speakappbackend.models.Pending;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ExerciseDTO {
    private long id;
    private String wordExercise;
    private int idGroupExercise;
    private String urlImageRelated;
    private String resultExpected;
    private int level;
    public ExerciseDTO(Exercise c) {
        this.id = c.getId();
        this.wordExercise = c.getWordExercise();
        this.idGroupExercise = c.getIdGroupExercise();
        this.urlImageRelated = c.getUrlImageRelated();
        this.resultExpected = c.getResultExpected();
        this.level = c.getLevel();
    }

}

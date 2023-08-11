package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.models.Exercise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateExerciseResponse {
    private long exerciseId;
    private String type;//enum EXERCISE_TYPE
    private String result;
    private Set<ImageDTO> images;

    public GenerateExerciseResponse(Exercise exercise) {
        this.exerciseId = exercise.getIdExercise();
        this.result = exercise.getResultExpected();
        this.type = exercise.getType();
        this.images = exercise
                .getImages()
                .stream()
                .map(ImageDTO::new)
                .collect(Collectors.toSet());
    }
}

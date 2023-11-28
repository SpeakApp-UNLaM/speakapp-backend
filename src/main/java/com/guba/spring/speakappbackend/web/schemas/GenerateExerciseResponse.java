package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.storages.database.models.Exercise;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateExerciseResponse {
    private Long idTaskItem;
    private TypeExercise type;
    private String result;
    private String incorrect;
    private List<ImageDTO> images = new ArrayList<>();

    public GenerateExerciseResponse(TaskItem taskItem) {
        this.idTaskItem = taskItem.getIdTask();
        final Exercise e = taskItem.getExercise();
        this.result = e.getResultExpected();
        this.type = e.getType();
        this.incorrect = e.getIncorrect();
        this.images = e
                .getImages()
                .stream()
                .map(ImageDTO::new)
                .collect(Collectors.toList());
        Collections.shuffle(this.images);
    }
}

package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.database.models.Image;
import com.guba.spring.speakappbackend.database.models.TaskItem;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResolveOrder implements ResolveStrategy {
    @Override
    public String resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        Image image = taskItem
                .getExercise()
                .getImages()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The exercise have not images"));

        return image.getDividedName().equalsIgnoreCase(resultExerciseDTO.getAudio()) ? "OK_EJERCICIO": "NO_EJERCICIO";
    }
}

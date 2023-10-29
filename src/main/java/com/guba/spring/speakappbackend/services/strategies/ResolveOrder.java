package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.storages.database.models.Image;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.guba.spring.speakappbackend.enums.ResultExercise.*;

@Component
@RequiredArgsConstructor
public class ResolveOrder implements ResolveStrategy {

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        Image image = taskItem
                .getExercise()
                .getImages()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The exercise have not images"));

        boolean isResolveSuccess = image.getDividedName().equalsIgnoreCase(resultExerciseDTO.getAudio());
        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        return taskItem;
    }
}

package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.storages.database.models.Image;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.storages.database.models.TaskItemDetail;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.guba.spring.speakappbackend.enums.ResultExercise.FAILURE;
import static com.guba.spring.speakappbackend.enums.ResultExercise.SUCCESS;

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
        Set<TaskItemDetail> taskItemDetails = new HashSet<>();
        taskItemDetails.add(new TaskItemDetail(image.getIdImage(), taskItem.getIdTask(), resultExerciseDTO.getAudio()));
        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        taskItem.setTaskItemDetails(taskItemDetails);
        return taskItem;
    }
}

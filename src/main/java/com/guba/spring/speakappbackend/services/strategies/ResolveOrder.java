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

        final String resultExpected = image.getDividedName().replace("-", " ");
        final String result = resultExerciseDTO.getAudio().replace("-", " ");

        boolean isResolveSuccess = resultExpected.equalsIgnoreCase(result);
        Set<TaskItemDetail> taskItemDetails = new HashSet<>();
        taskItemDetails.add(new TaskItemDetail(image.getIdImage(), taskItem.getIdTask(), result));
        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        taskItem.setTaskItemDetails(taskItemDetails);
        return taskItem;
    }
}

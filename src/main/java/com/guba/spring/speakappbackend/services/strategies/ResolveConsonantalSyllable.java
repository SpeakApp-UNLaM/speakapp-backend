package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.enums.ResultExercise;
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
public class ResolveConsonantalSyllable implements ResolveStrategy {

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        final String resultExpected = taskItem.getExercise().getResultExpected();
        Set<TaskItemDetail> taskItemDetails = new HashSet<>();
        var image = resultExerciseDTO.getSelectionImages().get(0);
        boolean isResolveSuccess = resultExerciseDTO
                .getSelectionImages()
                .stream()
                .allMatch(imageSelected -> resultExpected.equalsIgnoreCase(imageSelected.getName()));

        taskItemDetails.add(new TaskItemDetail(image.getIdImage(), taskItem.getIdTask(), image.getName()));
        taskItemDetails.add(new TaskItemDetail(image.getIdImage(), taskItem.getIdTask(), null));
        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        taskItem.setTaskItemDetails(taskItemDetails);
        return taskItem;
    }

}

package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.guba.spring.speakappbackend.enums.ResultExercise.*;

@Component
@RequiredArgsConstructor
public class ResolveConsonantalSyllable implements ResolveStrategy {

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        final String resultExpected = taskItem.getExercise().getResultExpected();

        boolean isResolveSuccess = resultExerciseDTO
                .getSelectionImages()
                .stream()
                .allMatch(imageSelected -> resultExpected.equalsIgnoreCase(imageSelected.getName()));

        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        return taskItem;
    }

}

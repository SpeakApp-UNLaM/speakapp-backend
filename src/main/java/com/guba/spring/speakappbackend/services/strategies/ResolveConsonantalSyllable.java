package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.database.models.TaskItem;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResolveConsonantalSyllable implements ResolveStrategy {

    @Override
    public String resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        final String resultExpected = taskItem.getExercise().getResultExpected();

        return resultExerciseDTO
                .getImagesResultDTO()
                .stream()
                .allMatch(e -> resultExpected.equalsIgnoreCase(e.getNameAudio()))
                ? "OK_EJERCICIO": "NO_EJERCICIO";
    }

}

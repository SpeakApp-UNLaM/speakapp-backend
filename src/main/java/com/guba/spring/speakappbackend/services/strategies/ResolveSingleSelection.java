package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.storages.database.models.Image;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.guba.spring.speakappbackend.enums.ResultExercise.*;

@Component
@RequiredArgsConstructor
public class ResolveSingleSelection implements ResolveStrategy {

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        final String resultExpected = taskItem.getExercise().getResultExpected();
        Map<Long, Image> imagesById = taskItem
                .getExercise()
                .getImages()
                .stream()
                .collect(Collectors.toMap(Image::getIdImage, Function.identity()));

        var imageSelected = resultExerciseDTO
                .getSelectionImages()
                .stream()
                .map(i-> imagesById.get(i.getIdImage()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The resul exercise have not image"));

        boolean isResolveSuccess = resultExpected.equalsIgnoreCase(imageSelected.getName());
        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        return taskItem;
    }
}

package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.repositories.database.models.Image;
import com.guba.spring.speakappbackend.repositories.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResolveWordMatchSelection implements ResolveStrategy {

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        Map<Long, Image> imagesById = taskItem
                .getExercise()
                .getImages()
                .stream()
                .collect(Collectors.toMap(Image::getIdImage, Function.identity()));

        var result = resultExerciseDTO
                .getSelectionImages()
                .stream()
                .map( imagesResult -> {
                    var image = imagesById.get(imagesResult.getIdImage());
                    return image.getName().equalsIgnoreCase(imagesResult.getName());
                })
                .collect(Collectors.toList());

        boolean isResolveSuccess = result.stream().allMatch(r -> r);
        ResultExercise resultExercise = ResultExercise.NO_SUCCESS;
        if (isResolveSuccess)
            resultExercise = ResultExercise.SUCCESS;

        taskItem.setResult(resultExercise.name());
        return taskItem;
    }

}

package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.storages.database.models.Image;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.storages.database.models.TaskItemDetail;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.guba.spring.speakappbackend.enums.ResultExercise.*;
import static com.guba.spring.speakappbackend.enums.TypeExercise.MULTIPLE_MATCH_SELECTION;

@Component
@RequiredArgsConstructor
public class ResolveWordMatchSelection implements ResolveStrategy {

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        TypeExercise type = taskItem.getExercise().getType();
        Map<Long, Image> imagesById = taskItem
                .getExercise()
                .getImages()
                .stream()
                .collect(Collectors.toMap(Image::getIdImage, Function.identity()));

        Set<TaskItemDetail> taskItemDetails = new HashSet<>();
        var result = resultExerciseDTO
                .getSelectionImages()
                .stream()
                .map( imagesResult -> {
                    var image = imagesById.get(imagesResult.getIdImage());
                    var resultExpected = (type == MULTIPLE_MATCH_SELECTION) ? image.getName(): taskItem.getExercise().getResultExpected();
                    taskItemDetails.add(new TaskItemDetail(image.getIdImage(), taskItem.getIdTask(), resultExpected));
                    return resultExpected.equalsIgnoreCase(imagesResult.getName());
                })
                .collect(Collectors.toList());

        imagesById
                .values()
                .stream()
                .filter(image -> taskItemDetails.stream().noneMatch(tid -> tid.getIdImageSelected().equals(image.getIdImage())))
                .forEach(image -> taskItemDetails.add(new TaskItemDetail(image.getIdImage(), taskItem.getIdTask(), null)));

        boolean isResolveSuccess = result.stream().allMatch(r -> r);
        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        taskItem.setTaskItemDetails(taskItemDetails);
        return taskItem;
    }

}

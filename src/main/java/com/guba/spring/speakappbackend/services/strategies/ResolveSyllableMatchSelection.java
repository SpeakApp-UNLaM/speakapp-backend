package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.storages.database.models.Image;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.storages.database.models.TaskItemDetail;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.guba.spring.speakappbackend.enums.ResultExercise.FAILURE;
import static com.guba.spring.speakappbackend.enums.ResultExercise.SUCCESS;

@Component
@RequiredArgsConstructor
public class ResolveSyllableMatchSelection implements ResolveStrategy {

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        final String resultExpected = taskItem.getExercise().getResultExpected();
        List<Long> idsImageCorrect = taskItem
                .getExercise()
                .getImages()
                .stream()
                .filter(image -> image.getName().contains(resultExpected))//TODO MIRAR LA FRASE CON FLAN,
                .map(Image::getIdImage)
                .collect(Collectors.toList());
        Set<TaskItemDetail> taskItemDetails = new HashSet<>();

        var result = resultExerciseDTO
                .getSelectionImages()
                .stream()
                .map( imageSelected -> {
                    taskItemDetails.add(new TaskItemDetail(imageSelected.getIdImage(), taskItem.getIdTask(), resultExpected));
                    return idsImageCorrect.contains(imageSelected.getIdImage());
                })
                .collect(Collectors.toList());

        taskItem
                .getExercise()
                .getImages()
                .stream()
                .filter(image -> taskItemDetails.stream().noneMatch(tid -> tid.getIdImageSelected().equals(image.getIdImage())))
                .forEach(image -> taskItemDetails.add(new TaskItemDetail(image.getIdImage(), taskItem.getIdTask(), null)));

        //TODAS LAS IMAGES SELECCIONADAS SON CORRECTAS Y LA CANTIDAD DE CORRECTAS ES LA EXPERADA
        boolean isResolveSuccess = result.stream().allMatch(r -> r) && result.size() == idsImageCorrect.size();
        ResultExercise resultExercise = FAILURE;
        if (isResolveSuccess)
            resultExercise = SUCCESS;

        taskItem.setResult(resultExercise);
        taskItem.setTaskItemDetails(taskItemDetails);
        return taskItem;
    }
}

package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.repositories.database.models.Image;
import com.guba.spring.speakappbackend.repositories.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

        var result = resultExerciseDTO
                .getSelectionImages()
                .stream()
                .map( imageSelected -> idsImageCorrect.contains(imageSelected.getIdImage()))
                .collect(Collectors.toList());

        //TODAS LAS IMAGES SELECCIONADAS SON CORRECTAS Y LA CANTIDAD DE CORRECTAS ES EXPERADA
        boolean isResolveSuccess = result.stream().allMatch(r -> r) && result.size() == idsImageCorrect.size();
        ResultExercise resultExercise = ResultExercise.NO_SUCCESS;
        if (isResolveSuccess)
            resultExercise = ResultExercise.SUCCESS;

        taskItem.setResult(resultExercise.name());
        return taskItem;
    }
}

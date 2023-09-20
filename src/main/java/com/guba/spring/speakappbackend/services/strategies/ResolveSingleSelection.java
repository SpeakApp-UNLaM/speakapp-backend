package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.database.models.Image;
import com.guba.spring.speakappbackend.database.models.TaskItem;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResolveSingleSelection implements ResolveStrategy {
    @Override
    public String resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        final String resultExpected = taskItem.getExercise().getResultExpected();
        Map<Long, Image> imagesById = taskItem
                .getExercise()
                .getImages()
                .stream()
                .collect(Collectors.toMap(Image::getIdImage, Function.identity()));

        var imageSelected = resultExerciseDTO
                .getImagesResultDTO()
                .stream()
                .map(i-> imagesById.get(i.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The resul exercise have not image"));
        return resultExpected.equalsIgnoreCase(imageSelected.getName()) ? "OK_EJERCICIO": "NO_EJERCICIO";
    }
}

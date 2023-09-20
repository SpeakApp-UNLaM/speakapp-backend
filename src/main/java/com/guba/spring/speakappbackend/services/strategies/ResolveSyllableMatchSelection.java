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
public class ResolveSyllableMatchSelection implements ResolveStrategy {

    @Override
    public String resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        Map<Long, Image> imagesById = taskItem
                .getExercise()
                .getImages()
                .stream()
                .collect(Collectors.toMap(Image::getIdImage, Function.identity()));

        var result = resultExerciseDTO
                .getImagesResultDTO()
                .stream()
                .map( imagesResult -> {
                    var image = imagesById.get(imagesResult.getId());
                    return image.getName().contains(imagesResult.getNameAudio());//TODO MIRAR LA FRASE CON FLAN,
                })
                .collect(Collectors.toList());

        return result.stream().allMatch(r -> r) ? "OK_EJERCICIO": "NO_EJERCICIO";
    }
}

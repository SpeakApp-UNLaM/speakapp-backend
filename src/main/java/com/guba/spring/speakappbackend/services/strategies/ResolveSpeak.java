package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.database.models.Exercise;
import com.guba.spring.speakappbackend.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.services.FileStorageService;
import com.guba.spring.speakappbackend.services.algorithm.MatchingAlgorithm;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ResolveSpeak implements ResolveStrategy {

    private final ClientWhisperApiCustom clientWhisper;

    private final MatchingAlgorithm matchingAlgorithm;
    private final FileStorageService fileStorageService;

    @Override
    public String resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        //save audio
        final Exercise exercise = taskItem.getExercise();
        final byte[] byteAudio = Base64
                .getDecoder()
                .decode(resultExerciseDTO.getAudio());

        //TODO FALTA LA CARPETA DEL USERNAME, EXAMPLE dir: {username}/{taskId}/{filename}
        final String folder = taskItem.getTask().getIdTaskGroup().toString();
        final String filename = String.format("%s-%s-%s.wav", taskItem.getIdTask(), exercise.getIdExercise(), exercise.getResultExpected());
        this.fileStorageService.save(byteAudio, folder, filename);

        //call api whisper
        Resource resourceData = this.fileStorageService.load(filename);
        var transcription = this.clientWhisper.getTranscription(resourceData);

        //match result
        var resultMatch = this.matchingAlgorithm.getMatchPercentage(transcription.getText(), exercise.getResultExpected());
        final String dirRelativeAudio = Path.of(folder, filename).toString();
        ResultExercise resultExercise = ResultExercise.NO_SUCCESS;
        if (resultMatch.getPercentageMatch() == 1D )
            resultExercise = ResultExercise.SUCCESS;

        taskItem.setUrlAudio(dirRelativeAudio);
        taskItem.setResult(resultExercise.name());
        return null;
    }
}

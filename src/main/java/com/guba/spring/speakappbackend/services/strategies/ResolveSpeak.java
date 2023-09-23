package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.repositories.database.models.Exercise;
import com.guba.spring.speakappbackend.repositories.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.repositories.filesystems.AudioStorageRepository;
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
    private final AudioStorageRepository audioStorageRepository;

    @Override
    public TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO) {
        //save audio
        final Exercise exercise = taskItem.getExercise();
        final byte[] byteAudio = Base64
                .getDecoder()
                .decode(resultExerciseDTO.getAudio());

        //TODO FALTA LA CARPETA DEL USERNAME, EXAMPLE dir: {username}/{taskId}/{filename}
        final String folder = taskItem.getTask().getIdTaskGroup().toString();
        final String filename = String.format("%s-%s-%s.ogg", taskItem.getIdTask(), exercise.getIdExercise(), exercise.getResultExpected());
        final String dirRelativeAudio = Path.of(folder, filename).toString();
        this.audioStorageRepository.save(byteAudio, folder, filename);

        //call api whisper
        Resource resourceData = this.audioStorageRepository.getAudioByFilename(dirRelativeAudio);
        var transcription = this.clientWhisper.getTranscription(resourceData);

        //match result
        var resultMatch = this.matchingAlgorithm.getMatchPercentage(transcription.getText().toLowerCase(), exercise.getResultExpected().toLowerCase());
        ResultExercise resultExercise = ResultExercise.NO_SUCCESS;
        if (resultMatch.getPercentageMatch() > 0.8D )
            resultExercise = ResultExercise.SUCCESS;

        taskItem.setUrlAudio(dirRelativeAudio);
        taskItem.setResult(resultExercise.name());
        return taskItem;
    }
}

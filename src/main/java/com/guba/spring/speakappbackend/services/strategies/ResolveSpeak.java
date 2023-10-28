package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.clients.ModeSpeak;
import com.guba.spring.speakappbackend.storages.database.models.Exercise;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.storages.filesystems.AudioStorageRepository;
import com.guba.spring.speakappbackend.services.algorithm.MatchingAlgorithm;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.guba.spring.speakappbackend.clients.ModeSpeak.FAST;
import static com.guba.spring.speakappbackend.clients.ModeSpeak.SLOW;

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

        //folder example: username/taskId/filename
        final String username = taskItem.getTask().getPatient().getUsername();
        final String idTask = taskItem.getTask().getIdTaskGroup().toString();
        final String folder = Path.of(username, idTask).toString();
        final String filename = String.format("%s-%s-%s.ogg", taskItem.getIdTask(), exercise.getIdExercise(), exercise.getResultExpected());
        final String dirRelativeAudio = Path.of(folder, filename).toString();
        this.audioStorageRepository.save(byteAudio, folder, filename);

        //call api whisper
        Resource resourceData = this.audioStorageRepository.getAudioByFilename(dirRelativeAudio);
        var resultExercise = getMajorResult(resourceData, exercise.getResultExpected());

        taskItem.setUrlAudio(dirRelativeAudio);
        taskItem.setResult(resultExercise.name());
        return taskItem;
    }

    private ResultExercise getResultExerciseSpeak(Resource resourceData, String resultExcepted, ModeSpeak mode) {
        //call api whisper
        var transcription = this.clientWhisper.getTranscription(resourceData, mode);

        //match result
        var resultMatch = this.matchingAlgorithm.getMatchPercentage(transcription.getText(), resultExcepted);
        ResultExercise resultExercise = ResultExercise.FAILURE;
        if (resultMatch.getPercentageMatch() > 0.8D )
            resultExercise = ResultExercise.SUCCESS;
        return resultExercise;
    }

    private ResultExercise getMajorResult(Resource resourceData, String resultExcepted) {
        int count = 2;
        //threads call api whisper
        var completableFutures = IntStream.range(0, count)
                .mapToObj(i -> {
                    ModeSpeak mode = (i <= 1 ? SLOW: FAST);
                    return CompletableFuture.supplyAsync(() -> this.getResultExerciseSpeak(resourceData, resultExcepted, mode));
                })
                .collect(Collectors.toList());

        //join threads
        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[] ::new)).join();

        var isSuccess = completableFutures
                .stream()
                .map(CompletableFuture::join)
                .anyMatch(resultExercise -> resultExercise == ResultExercise.SUCCESS)
                ;

        if (isSuccess)
            return ResultExercise.SUCCESS;
        return ResultExercise.FAILURE;
    }
}

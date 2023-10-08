package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.ResolveExerciseService;
import com.guba.spring.speakappbackend.web.schemas.PhonemeDTO;
import com.guba.spring.speakappbackend.web.schemas.ResolutionTaskDTO;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import com.guba.spring.speakappbackend.web.schemas.TaskItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "resolve-exercises")
@Slf4j
public class ResolveExerciseController {

    private final ResolveExerciseService resolveExerciseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resolveExercises(@RequestBody List<ResultExerciseDTO> resolveExercises) {
        log.info("resolve exercises: {}", resolveExercises);
        resolveExerciseService.resolve(resolveExercises);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping(path = "/{idPatient}")
    public ResponseEntity<List<PhonemeDTO>> getPhonemesResolved(@PathVariable Long idPatient) {
        log.info("get phonemes resolved of patient: {}", idPatient);
        return ResponseEntity
                .ok()
                .body(resolveExerciseService.getPhonemesResolvedByPatient(idPatient));
    }

    @GetMapping(path = "/{idPatient}/{idPhoneme}")
    public ResponseEntity<ResolutionTaskDTO> getTasksResolvedByPhoneme(@PathVariable Long idPatient, @PathVariable Long idPhoneme) {
        log.info("get tasks resolved of phoneme: {}, patient: {}", idPhoneme, idPatient);
        return ResponseEntity
                .ok()
                .body(resolveExerciseService.getExercisesResolvedByPatientAndPhoneme(idPatient, idPhoneme));
    }

    @GetMapping(path = "/task/{idTask}")
    public ResponseEntity<List<TaskItemDTO>> getExercisesResolvedByTask(@PathVariable Long idTask) {
        log.info("get exercises resolved of task: {}", idTask);
        return ResponseEntity
                .ok()
                .body(resolveExerciseService.getExercisesResolvedByTask(idTask));
    }
}
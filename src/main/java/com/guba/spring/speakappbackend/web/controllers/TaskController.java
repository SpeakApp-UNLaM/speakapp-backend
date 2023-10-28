package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.TaskService;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping(path = "/{idPatient}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<PhonemeCategoryDTO>> createTask(@PathVariable Long idPatient, @RequestBody GenerateExerciseRequest requests) {
        var response = taskService.createTask(idPatient, requests);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idPatient}")
    public ResponseEntity<Set<PhonemeCategoryDTO>> getTasks(@PathVariable Long idPatient) {
        var response = taskService.getTasksByPatient(idPatient);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{idPatient}/{idPhoneme}")
    public ResponseEntity<PhonemeCategoryDTO> getTaskByPhoneme(@PathVariable Long idPatient, @PathVariable Long idPhoneme) {
        var response = taskService.getTaskByPatientAndPhoneme(idPatient, idPhoneme);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{idTask}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long idTask) {
        taskService.deleteTask(idTask);
        return ResponseEntity
                .noContent()
                .build();
    }
}

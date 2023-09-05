package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.TaskService;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<PhonemeCategoryDTO>> createTask(@RequestBody GenerateExerciseRequest requests) {
        var response = taskService.createTask(requests);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenerateExerciseResponse>> createTaskItems(@RequestBody GenerateExerciseRequest request) {
        var response = taskService.createTaskItems(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idPatient}/{idPhoneme}")
    public ResponseEntity<List<GenerateExerciseResponse>> getTaskItemsByPhoneme(@PathVariable Long idPatient, @PathVariable Long idPhoneme) {
        var response = taskService.getTaskItemsByPhoneme(idPatient, idPhoneme);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{idPatient}")
    public ResponseEntity<Set<PhonemeCategoryDTO>> getTasksPhoneme(@PathVariable Long idPatient) {
        var response = taskService.getTasksPhoneme(idPatient);
        return ResponseEntity.ok(response);
    }
}

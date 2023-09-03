package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.TaskService;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTask(@RequestBody GenerateExerciseRequest requests) {
        taskService.createTask(requests);
        return new ResponseEntity<>("Task created successfully!", HttpStatus.CREATED);
    }

    @PostMapping(path = "/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenerateExerciseResponse>> createTaskItems(@RequestBody List<GenerateExerciseRequest> requests) {
        var response = taskService.createTaskItems(requests.get(0));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idPatient}")
    public ResponseEntity<List<GenerateExerciseResponse>> getTask(@PathVariable Long idPatient) {
        var response = taskService.getTaskItems(idPatient);
        return ResponseEntity.ok(response);
    }
}

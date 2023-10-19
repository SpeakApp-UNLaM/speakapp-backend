package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.enums.TypeExercise;
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
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "task-items")
@Slf4j
public class TaskItemController {

    private final TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenerateExerciseResponse>> createTaskItems(@RequestBody GenerateExerciseRequest request) {
        var response = taskService.createTaskItems(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/current/{idPatient}/{idPhoneme}")
    public ResponseEntity<List<GenerateExerciseResponse>> getTaskItemsByPhoneme(@PathVariable Long idPatient, @PathVariable Long idPhoneme) {
        var response = taskService.getTaskItemsByPhoneme(idPatient, idPhoneme);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/generate-custom")
    public ResponseEntity<List<GenerateExerciseResponse>> createTaskAllTypeExercise(
            @RequestParam(name = "typesExercise") Set<TypeExercise> typesExercise,
            @RequestParam(name = "idsPhoneme") Set<Long> idsPhoneme,
            @RequestParam(name = "categories") Set<Category> categories,
            @RequestParam(name = "levels") Set<Integer> levels
    ) {
        var response = taskService.createTaskItems(typesExercise, levels, idsPhoneme, categories);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

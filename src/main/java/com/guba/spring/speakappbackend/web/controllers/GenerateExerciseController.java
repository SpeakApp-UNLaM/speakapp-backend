package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import com.guba.spring.speakappbackend.services.GenerateExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "generate-exercises")
@Slf4j
public class GenerateExerciseController {

    private final GenerateExerciseService generateExerciseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenerateExerciseResponse>> generateExercise(@RequestBody List<GenerateExerciseRequest> requests) {
        var response = generateExerciseService.generateExercise(requests.get(0));
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "fixme", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTask(@RequestBody GenerateExerciseRequest requests) {
        generateExerciseService.createTask(requests);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idPatient}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTask(@PathVariable Long idPatient) {
        generateExerciseService.getTask(idPatient);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.CREATED);
    }
}

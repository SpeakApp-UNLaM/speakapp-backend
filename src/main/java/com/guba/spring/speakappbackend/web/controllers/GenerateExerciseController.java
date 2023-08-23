package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import com.guba.spring.speakappbackend.services.GenerateExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

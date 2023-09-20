package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.ResolveExerciseService;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
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
@RequestMapping(value = "resolve-exercises")
@Slf4j
public class ResolveExerciseController {

    private final ResolveExerciseService resolveExerciseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resolveExercises(@RequestBody List<ResultExerciseDTO> resolveExercises) {
        resolveExerciseService.resolve(resolveExercises);
        return ResponseEntity
                .ok()
                .build();
    }
}
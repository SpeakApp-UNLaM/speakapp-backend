package com.guba.spring.speakappbackend.controllers;

import com.guba.spring.speakappbackend.schemas.ExerciseDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import com.guba.spring.speakappbackend.services.ExerciseService;
import com.guba.spring.speakappbackend.services.PendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }
    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseDTO>> getExercise() {
        log.info("Obtener todos los care");
        List<ExerciseDTO> exercise = this.exerciseService.getExercise();
        return ResponseEntity.ok(exercise);
    }
    @GetMapping("/exercise/{name}")
    public ResponseEntity<List<ExerciseDTO>> getExerciseByName(@PathVariable int name) {
        log.info("Obtener todos los care");
        List<ExerciseDTO> exercise = this.exerciseService.getExerciseByName(name);
        return ResponseEntity.ok(exercise);
    }

    @PostMapping("/exercise")
    public ResponseEntity<ExerciseDTO> createExercise(@RequestBody ExerciseDTO exerciseDTO){
        log.info("Obtener todos los care");
        ExerciseDTO exercise = this.exerciseService.saveExercise(exerciseDTO);
        return ResponseEntity.ok(exercise);
    }
}

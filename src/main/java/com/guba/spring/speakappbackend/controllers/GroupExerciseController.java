package com.guba.spring.speakappbackend.controllers;

import com.guba.spring.speakappbackend.schemas.GroupExerciseDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import com.guba.spring.speakappbackend.services.GroupExerciseService;
import com.guba.spring.speakappbackend.services.PendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class GroupExerciseController {

    private final GroupExerciseService groupExerciseService;

    @Autowired
    public GroupExerciseController(GroupExerciseService groupExerciseService) {
        this.groupExerciseService = groupExerciseService;
    }
    @GetMapping("/groupExercises")
    public ResponseEntity<List<GroupExerciseDTO>> getGroupExercise() {
        log.info("Obtener todos los care");
        List<GroupExerciseDTO> groupExercise = this.groupExerciseService.getGroupExercise();
        return ResponseEntity.ok(groupExercise);
    }
    @GetMapping("/groupExercise/{name}")
    public ResponseEntity<List<GroupExerciseDTO>> getGroupExerciseByName(@PathVariable int name) {
        log.info("Obtener todos los care");
        List<GroupExerciseDTO> groupExercise = this.groupExerciseService.getGroupExerciseByName(name);
        return ResponseEntity.ok(groupExercise);
    }

    @PostMapping("/groupExercise")
    public ResponseEntity<GroupExerciseDTO> createGroupExercise(@RequestBody GroupExerciseDTO groupExerciseDTO){
        log.info("Obtener todos los care");
        GroupExerciseDTO groupExerciseDT = this.groupExerciseService.saveGroupExercise(groupExerciseDTO);
        return ResponseEntity.ok(groupExerciseDT);
    }
}

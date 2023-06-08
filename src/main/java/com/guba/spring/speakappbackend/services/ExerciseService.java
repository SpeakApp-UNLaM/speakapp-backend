package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.models.Exercise;
import com.guba.spring.speakappbackend.models.Pending;
import com.guba.spring.speakappbackend.repositories.ExerciseRepository;
import com.guba.spring.speakappbackend.repositories.PendingRepository;
import com.guba.spring.speakappbackend.schemas.ExerciseDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<ExerciseDTO> getExercise() {
        log.info("obtener Carrers de la bbdd");
        return exerciseRepository.findAll()
                .stream()
                .map(ExerciseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ExerciseDTO> getExerciseByName(int name) {
        log.info("obtener Carrers por nombre de la bbdd");
        return exerciseRepository.getExerciseByName(name)
                .stream()
                .map(ExerciseDTO::new)
                .collect(Collectors.toList());
    }


    public ExerciseDTO saveExercise(ExerciseDTO exerciseDTO) {
        log.info("crear Exercise en la bbdd");
        var exercise = new Exercise(exerciseDTO);
        var exerciseUpdate =  this.exerciseRepository.save(exercise);
        return new ExerciseDTO(exerciseUpdate);
    }
}

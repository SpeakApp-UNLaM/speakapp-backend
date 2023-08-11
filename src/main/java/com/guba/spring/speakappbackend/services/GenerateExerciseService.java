package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.models.Exercise;
import com.guba.spring.speakappbackend.models.Task;
import com.guba.spring.speakappbackend.models.TaskItem;
import com.guba.spring.speakappbackend.repositories.*;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final TaskRepository taskRepository;
    private final TaskItemRepository taskItemRepository;
    private final PatientRepository patientRepository;


    @Transactional
    public List<GenerateExerciseResponse> generateExercise(GenerateExerciseRequest request) {

        List<Exercise> exercises = exerciseRepository.findALLByCategoryAndLevelAndPhoneme(request.getCategories(), request.getLevel(), request.getPhonemeId());

        Task task = new Task();
        task.setPatient(patientRepository.getById(1L));
        task.setStatus("status");

        Task taskCreated = taskRepository.save(task);
        var items = exercises
                .stream()
                .map( exercise -> {
                    TaskItem item = new TaskItem();
                    item.setExercise(exercise);
                    item.setTask(taskCreated);
                    item.setUrlAudio("url");
                    item.setResult("result");
                    return item;
                })
                .collect(Collectors.toSet());
        taskItemRepository.saveAll(items);


        return exercises
                .stream()
                .map(GenerateExerciseResponse::new)
                .collect(Collectors.toList());
    }
}

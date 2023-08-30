package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.database.models.*;
import com.guba.spring.speakappbackend.database.repositories.*;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    private final ProfessionalRepository professionalRepository;

    private final SelectionService selectionService;


    @Transactional
    public List<GenerateExerciseResponse> generateExercise(GenerateExerciseRequest request) {

        List<Exercise> exercises = exerciseRepository.findALLByCategoryAndLevelAndPhoneme(request.getCategories(), request.getLevel(), request.getPhonemeId());

        this.taskRepository.findAllByPatientAndStatusAndBetween(1L, TaskStatus.CREATED, LocalDate.now())
                .stream()
                .filter(t -> request.getCategories().contains(t.getCategory()))
                .filter(t -> t.getLevel() == request.getLevel())
                .collect(Collectors.toList());
        List<Exercise> exerciseSelected = selectionService.selectionExercisesByPhonemeAndLevelAndCategory(exercises);
        Task task = new Task();
        task.setPatient(patientRepository.getById(1L));
        task.setStatus(TaskStatus.PROGRESSING);

        Task taskCreated = taskRepository.save(task);
        var items = exerciseSelected
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

    @Transactional
    public void createTask(GenerateExerciseRequest request) {
        Patient patient = this.patientRepository.findById(1L).orElseThrow(IllegalAccessError::new);
        Professional professional = this.professionalRepository.findById(1L).orElseThrow(IllegalAccessError::new);
        LocalDate now = LocalDate.now();

        var tasks = request
                .getCategories()
                .stream().map(c-> {
                    Task task = new Task();
                    Phoneme phoneme = new Phoneme();
                    phoneme.setIdPhoneme(request.getPhonemeId());
                    task.setPatient(patient);
                    task.setProfessional(professional);
                    task.setLevel(request.getLevel());
                    task.setCategory(c);
                    task.setPhoneme(phoneme);
                    task.setStatus(TaskStatus.CREATED);
                    task.setStartDate(now);
                    task.setEndDate(now.plusWeeks(2));

                    return task;
                }).collect(Collectors.toList());

        taskRepository.saveAll(tasks);
    }

    public void getTask(Long idPatient) {

        List<Task> tasks = this.taskRepository.findAllByPatientAndStatusAndBetween(idPatient, TaskStatus.CREATED, LocalDate.now());

    }
}

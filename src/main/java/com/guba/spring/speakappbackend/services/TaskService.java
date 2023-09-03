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
public class TaskService {

    private final ExerciseRepository exerciseRepository;
    private final TaskRepository taskRepository;
    private final TaskItemRepository taskItemRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final SelectionService selectionService;

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

    @Transactional
    public List<GenerateExerciseResponse> createTaskItems(GenerateExerciseRequest request) {

        List<Exercise> exercises = exerciseRepository.findAllByCategoriesAndLevelAndPhoneme(request.getCategories(), request.getLevel(), request.getPhonemeId());

        List<Exercise> exerciseSelected = selectionService.selectionExercisesByPhonemeAndLevelAndCategory(exercises);

        List<Task> tasks = this.taskRepository.findAllByPatientAndStatusAndBetween(1L, TaskStatus.CREATED, LocalDate.now())
                .stream()
                .filter(t -> request.getPhonemeId() == t.getPhoneme().getIdPhoneme())
                .filter(t -> request.getCategories().contains(t.getCategory()))
                .filter(t -> request.getLevel() == t.getLevel())
                .collect(Collectors.toList());

        var items = exerciseSelected
                .stream()
                .map(e -> {
                    var taskFound = tasks.stream()
                            .filter(t -> e.getCategory() == t.getCategory())
                            .filter(t -> e.getPhonemes().contains(t.getPhoneme()))
                            .filter(t -> e.getLevel() == t.getLevel())
                            .findFirst()
                            .orElseThrow(IllegalArgumentException::new);
                    TaskItem item = new TaskItem();
                    item.setExercise(e);
                    item.setTask(taskFound);
                    item.setUrlAudio("url");
                    item.setResult("result");
                    return item;
                })
                .collect(Collectors.toSet());
        taskItemRepository.saveAll(items);

        return exerciseSelected
                .stream()
                .map(GenerateExerciseResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GenerateExerciseResponse> getTaskItems(Long idPatient) {

        List<Task> tasks = this.taskRepository.findAllByPatientAndStatusAndBetween(idPatient, TaskStatus.CREATED, LocalDate.now());

        return tasks
                .stream()
                .flatMap(t -> t.getTaskItems().stream())
                .map(TaskItem::getExercise)
                .map(GenerateExerciseResponse::new)
                .collect(Collectors.toList());
    }
}

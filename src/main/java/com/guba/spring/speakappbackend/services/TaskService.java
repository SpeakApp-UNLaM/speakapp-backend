package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import com.guba.spring.speakappbackend.storages.database.models.*;
import com.guba.spring.speakappbackend.storages.database.repositories.*;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO.CategoryDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final ExerciseRepository exerciseRepository;
    private final TaskRepository taskRepository;
    private final TaskItemRepository taskItemRepository;
    private final PatientRepository patientRepository;
    private final SelectionService selectionService;
    private final CustomUserDetailService customUserDetailService;

    @Transactional
    public Set<PhonemeCategoryDTO> createTask(Long idPatient, GenerateExerciseRequest request) {
        Patient patient = this.patientRepository
                .findById(idPatient)
                .orElseThrow( () -> new NotFoundElementException("Not found patient for the id " + idPatient));

        Professional professional = this.customUserDetailService.getUserCurrent(Professional.class);
        LocalDateTime now = LocalDateTime.now();

        var tasks = request
                .getCategories()
                .stream().map(c-> {
                    Task task = new Task();
                    Phoneme phoneme = new Phoneme();
                    phoneme.setIdPhoneme(request.getIdPhoneme());
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

        return taskRepository
                .saveAll(tasks)
                .stream()
                .collect(Collectors.groupingBy(
                        Task::getPhoneme,
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(entry -> converterToPhonemeCategoryDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    @Transactional
    public List<GenerateExerciseResponse> createTaskItems(GenerateExerciseRequest request) {

        List<Exercise> exercises = exerciseRepository.findAllByCategoriesAndLevelAndPhoneme(request.getCategories(), request.getLevel(), request.getIdPhoneme());

        List<Exercise> exerciseSelected = selectionService.selectionExercisesByPhonemeAndLevelAndCategory(exercises);
        Patient patient = this.customUserDetailService.getUserCurrent(Patient.class);

        List<Task> tasks = this.taskRepository.findAllByPatientAndPhonemeStatusAndBetween(patient.getIdPatient(), request.getIdPhoneme(), TaskStatus.CREATED, LocalDateTime.now())
                .stream()
                .filter(t -> request.getCategories().contains(t.getCategory()))
                .filter(t -> request.getLevel() == t.getLevel())
                .collect(Collectors.toList());

        List<TaskItem> taskItemsCreatedPreviously = tasks.stream().flatMap(task -> task.getTaskItems().stream()).collect(Collectors.toList());
        this.taskItemRepository.deleteAll(taskItemsCreatedPreviously);

        var items = exerciseSelected
                .stream()
                .map(e -> {
                    var taskFound = tasks.stream()
                            .filter(t -> e.getCategory() == t.getCategory())
                            .filter(t -> e.getPhoneme().equals(t.getPhoneme()))
                            .filter(t -> e.getLevel() == t.getLevel())
                            .findFirst()
                            .orElseThrow(IllegalArgumentException::new);
                    return new TaskItem(taskFound, e, null, null);
                })
                .collect(Collectors.toSet());
        taskItemRepository.saveAll(items);

        return items
                .stream()
                .map(GenerateExerciseResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GenerateExerciseResponse> getTaskItemsByPhoneme(Long idPatient, Long idPhoneme) {
        return this.taskRepository
                .findAllByPatientAndPhonemeStatusAndBetween(idPatient, idPhoneme, TaskStatus.CREATED, LocalDateTime.now())
                .stream()
                .flatMap(t -> t.getTaskItems().stream())
                .map(GenerateExerciseResponse::new)
                .collect(Collectors.toList());
    }

    public Set<PhonemeCategoryDTO> getTasksByPatient(Long idPatient) {
        return this.taskRepository
                .findAllByPatientAndStatusAndBetween(idPatient, TaskStatus.CREATED, LocalDateTime.now())
                .stream()
                .collect(Collectors.groupingBy(
                        Task::getPhoneme,
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(entry -> converterToPhonemeCategoryDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    private PhonemeCategoryDTO converterToPhonemeCategoryDTO(Phoneme phoneme, List<Task> tasks) {
        var categories = tasks
                .stream()
                .map(task -> CategoryDTO
                        .builder()
                        .idTask(task.getIdTaskGroup())
                        .category(task.getCategory())
                        .level(task.getLevel())
                        .build())
                .collect(Collectors.toSet());
        return new PhonemeCategoryDTO(phoneme, categories);
    }

    public List<GenerateExerciseResponse> createTaskItems(Set<TypeExercise> typesExercise, Set<Integer> levels, Set<Long> idsPhoneme, Set<Category> categories) {
        //TODO FIXED
        Patient patient = this.patientRepository
                .findById(1L)
                .orElseThrow( () -> new NotFoundElementException("Not found patient for the id " + 1));

        Professional professional = this.customUserDetailService.getUserCurrent(Professional.class);
        LocalDateTime now = LocalDateTime.now();
        List<Exercise> exercises = this.exerciseRepository
                .findAllByCategoriesAndPhonemesAndTypes(categories, idsPhoneme, typesExercise)
                .stream()
                .filter(exercise -> levels.contains(exercise.getLevel()))
                .collect(Collectors.toList());

        Function<Exercise, String> generateKey = e -> e.getType().getName() + e.getCategory().getName() + e.getPhoneme().getIdPhoneme() + e.getLevel();

        Collections.shuffle(exercises);
        var oneExerciseSelectedByTypeCategoryLevelPhoneme = exercises
                .stream()
                .collect(Collectors.toMap(
                        generateKey,
                        Function.identity(),
                        (e1, e2) -> e2
                ));

        Set<Task> taskGenerated = oneExerciseSelectedByTypeCategoryLevelPhoneme
                .values()
                .stream()
                .collect(Collectors.groupingBy(
                        e -> {
                            Task task = new Task();
                            task.setPatient(patient);
                            task.setProfessional(professional);
                            task.setLevel(e.getLevel());
                            task.setCategory(e.getCategory());
                            task.setPhoneme(e.getPhoneme());
                            task.setStatus(TaskStatus.CREATED);
                            task.setStartDate(now);
                            task.setEndDate(now.plusWeeks(2));

                            return task;
                        },
                        Collectors.toSet()
                ))
                .entrySet()
                .stream()
                .map(entry-> {
                    var taskCreated = this.taskRepository.save(entry.getKey());
                    var taskItems = entry
                            .getValue()
                            .stream()
                            .map(e-> new TaskItem(taskCreated, e, null, null))
                            .collect(Collectors.toSet());
                    taskCreated.setTaskItems(taskItems);
                    this.taskItemRepository.saveAll(taskItems);
                    return taskCreated;
                })
                .collect(Collectors.toSet());

        return taskGenerated
                .stream()
                .flatMap(t->t.getTaskItems().stream())
                .map(GenerateExerciseResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteTask(Long idTask) {
        this.taskRepository
                .findById(idTask)
                .ifPresent(task -> {
                    taskItemRepository.deleteAll(task.getTaskItems());
                    this.taskRepository.delete(task);
                });
    }

    public PhonemeCategoryDTO getTaskByPatientAndPhoneme(Long idPatient, Long idPhoneme) {
        return this.getTasksByPatient(idPatient)
                .stream()
                .filter(t -> t.getPhoneme().getIdPhoneme().equals(idPhoneme))
                .findFirst()
                .orElse(new PhonemeCategoryDTO());
                //.orElseThrow(()-> new NotFoundElementException("Not found task for patient " + idPatient + " and phoneme {}" +idPatient));
    }
}

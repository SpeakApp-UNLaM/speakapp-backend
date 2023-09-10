package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.database.models.*;
import com.guba.spring.speakappbackend.database.repositories.*;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseRequest;
import com.guba.spring.speakappbackend.web.schemas.GenerateExerciseResponse;
import com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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
    private final ProfessionalRepository professionalRepository;
    private final SelectionService selectionService;

    @Transactional
    public Set<PhonemeCategoryDTO> createTask(GenerateExerciseRequest request) {
        final Long idPatient = request.getIdPatient();
        Patient patient = this.patientRepository
                .findById(idPatient)
                .orElseThrow( () -> new NotFoundElementException("Not found patient for the id " + idPatient));

        Professional professional = this.professionalRepository.findById(1L).orElseThrow(IllegalAccessError::new);
        LocalDate now = LocalDate.now();

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

        List<Task> tasks = this.taskRepository.findAllByPatientAndPhonemeStatusAndBetween(1L, request.getIdPhoneme(), TaskStatus.CREATED, LocalDate.now())
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
    public List<GenerateExerciseResponse> getTaskItemsByPhoneme(Long idPatient, Long idPhoneme) {
        return this.taskRepository
                .findAllByPatientAndPhonemeStatusAndBetween(idPatient, idPhoneme, TaskStatus.CREATED, LocalDate.now())
                .stream()
                .filter(task -> idPhoneme.equals(task.getPhoneme().getIdPhoneme()))
                .flatMap(t -> t.getTaskItems().stream())
                .map(TaskItem::getExercise)
                .map(GenerateExerciseResponse::new)
                .collect(Collectors.toList());
    }

    public Set<PhonemeCategoryDTO> getTasksPhoneme(Long idPatient) {
        return this.taskRepository
                .findAllByPatientAndStatusAndBetween(idPatient, TaskStatus.CREATED, LocalDate.now())
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
                        .category(task.getCategory())
                        .level(task.getLevel())
                        .build())
                .collect(Collectors.toSet());
        return new PhonemeCategoryDTO(phoneme, categories);
    }
}

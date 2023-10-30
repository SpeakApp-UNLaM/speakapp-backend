package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.services.strategies.ResolveStrategy;
import com.guba.spring.speakappbackend.storages.database.models.Phoneme;
import com.guba.spring.speakappbackend.storages.database.models.Task;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.storages.database.repositories.TaskItemDetailRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.TaskItemRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.TaskRepository;
import com.guba.spring.speakappbackend.storages.filesystems.AudioStorageRepository;
import com.guba.spring.speakappbackend.web.schemas.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.guba.spring.speakappbackend.enums.ResultExercise.*;
import static com.guba.spring.speakappbackend.enums.TaskStatus.*;
import static com.guba.spring.speakappbackend.enums.TypeExercise.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResolveExerciseService {

    private final Map<TypeExercise, ResolveStrategy> resolveStrategyByType;
    private final TaskItemRepository taskItemRepository;
    private final TaskRepository taskRepository;
    private final AudioStorageRepository audioStorageRepository;
    private final TaskItemDetailRepository taskItemDetailRepository;

    public void resolve(List<ResultExerciseDTO> exercisesDTO) {
        //task, set taskStatus to PROGRESSING
        setTaskStatus(exercisesDTO, PROGRESSING);

        //resolve exercises
        List<TaskItem> taskItemsResolved = exercisesDTO
                .stream()
                .parallel()
                .map(this::resolveExercise)
                .collect(Collectors.toList());
        this.taskItemRepository.saveAll(taskItemsResolved);

        //task, set taskStatus to DONE
        setTaskStatus(exercisesDTO, DONE);
    }

    private TaskItem resolveExercise(ResultExerciseDTO resultExerciseDTO) {
        //delete task item detail previous
        this.taskItemDetailRepository.deleteByIdTaskItem(resultExerciseDTO.getIdTaskItem());
        return taskItemRepository
                .findById(resultExerciseDTO.getIdTaskItem())
                .map(taskItem -> {
                    taskItem.getTaskItemDetails().clear();
                    taskItem.setResult(FAILURE);
                    TypeExercise type = taskItem.getExercise().getType();
                    try {
                        return resolveStrategyByType.get(type).resolve(taskItem, resultExerciseDTO);
                    } catch (Exception e) {
                        log.error("Error in resolution exercises", e);
                    }
                    return taskItem;
                })
                .orElseThrow(() -> new IllegalArgumentException("no puede haber taskitem vacio"));
    }

    public List<PhonemeDTO> getPhonemesResolvedByPatient(Long idPatient) {
        return this.taskRepository
                .findAllByPatientAndStatus(idPatient, DONE)
                .stream()
                .map(task -> new PhonemeDTO(task.getPhoneme()))
                .distinct()
                .sorted(Comparator.comparing(PhonemeDTO::getNamePhoneme))
                .collect(Collectors.toList());
    }

    public ResolutionTaskDTO getExercisesResolvedByPatientAndPhoneme(Long idPatient, Long idPhoneme) {
        return this.taskRepository
                .findAllByPatientAndStatus(idPatient, DONE)
                .stream()
                .filter(task -> task.getPhoneme().getIdPhoneme().equals(idPhoneme))
                .collect(Collectors.groupingBy(
                        Task::getPhoneme,
                        Collectors.toList()))
                .entrySet()
                .stream()
                .map(entry -> converterToResolutionTaskDTO(entry.getKey(), entry.getValue()))
                .findFirst()
                .orElseThrow(() -> new NotFoundElementException("not found task completed for Patient: " + idPatient + " and phoneme: " + idPhoneme));
    }

    public List<TaskItemDTO> getExercisesResolvedByTask(Long idTask) {
        return this.taskRepository
                .findById(idTask)
                .stream()
                .flatMap(task -> task.getTaskItems().stream())
                .map(taskItem -> {
                    String audioBase64 = Optional
                            .of(taskItem.getExercise())
                            .filter(e -> e.getType() == SPEAK)
                            .map(e -> this.audioStorageRepository.getAudioBase64ByFilename(taskItem.getUrlAudio()))
                            .orElse("");
                    return new TaskItemDTO(taskItem.getIdTask(), taskItem.getResult(), taskItem.getExercise().getType(), audioBase64, taskItem.getExercise().getResultExpected());
                })
                .collect(Collectors.toList());
    }

    private ResolutionTaskDTO converterToResolutionTaskDTO(Phoneme p, List<Task> tasks) {
        List<TaskDTO> tasksDTO = tasks
                .stream()
                .sorted(Comparator.comparing(Task::getEndDate).reversed())
                .map(task -> new TaskDTO(task.getIdTaskGroup(), task.getLevel(), task.getCategory(), task.getEndDate()))
                .collect(Collectors.toList());
        return new ResolutionTaskDTO(p.getIdPhoneme(), p.getNamePhoneme(), tasksDTO);
    }

    private void setTaskStatus(List<ResultExerciseDTO> exercisesDTO, TaskStatus status) {
        LocalDateTime now = LocalDateTime.now();
        Collection<Task> tasks = exercisesDTO
                .stream()
                .parallel()
                .map(ResultExerciseDTO::getIdTaskItem)
                .map(idTaskItem -> taskItemRepository.findById(idTaskItem).orElse(null))
                .filter(Objects::nonNull)
                .map(TaskItem::getTask)
                .distinct()
                .peek(task -> {
                    task.setStatus(status);
                    task.setEndDate(now);
                })
                .collect(Collectors.toList());

        this.taskRepository.saveAll(tasks);
    }

    public void manualCorrection(Long idTaskItem, ResultExercise result) {
        TaskItem taskItem = this.taskItemRepository
                .findById(idTaskItem)
                .orElseThrow(() -> new NotFoundElementException("Not found task item " + idTaskItem))
                ;

        taskItem.setResult(result);
        this.taskItemRepository.save(taskItem);
    }

    public List<TaskItemDetailDTO> getTaskItemDetail(Long idTaskItem) {
        Optional<TaskItem> taskItem = this.taskItemRepository.findById(idTaskItem);
        if(taskItem.isEmpty())
            return List.of();
        TypeExercise type = taskItem.get().getExercise().getType();

        return taskItem
                .stream()
                .flatMap(ti -> ti.getTaskItemDetails().stream())
                .map(detail -> {
                    var resultExpected = detail.getResultSelected();
                    var resultSelected = detail.getImageSelected().getName();
                    if (type == ORDER_SYLLABLE) {
                        resultExpected = detail.getImageSelected().getDividedName();
                        resultSelected = detail.getResultSelected();
                    } else if (type == CONSONANTAL_SYLLABLE) {
                        resultExpected = Optional
                                .ofNullable(detail.getResultSelected())
                                .map(s-> detail.getImageSelected().getName())
                                .orElse(null);
                        resultSelected = Optional
                                .ofNullable(detail.getResultSelected())
                                .filter(r-> r.equals(taskItem.get().getExercise().getResultExpected()))
                                .map(s -> taskItem.get().getExercise().getResultExpected())
                                .orElse(taskItem.get().getExercise().getIncorrect());
                    }
                    return new TaskItemDetailDTO(resultExpected, resultSelected);
                })
                .collect(Collectors.toList());
    }
}

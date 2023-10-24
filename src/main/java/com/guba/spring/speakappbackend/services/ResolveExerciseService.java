package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.storages.database.models.Phoneme;
import com.guba.spring.speakappbackend.storages.database.models.Task;
import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.storages.database.repositories.TaskItemRepository;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.services.strategies.ResolveStrategy;
import com.guba.spring.speakappbackend.storages.database.repositories.TaskRepository;
import com.guba.spring.speakappbackend.storages.filesystems.AudioStorageRepository;
import com.guba.spring.speakappbackend.web.schemas.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResolveExerciseService {

    private final Map<TypeExercise, ResolveStrategy> resolveStrategyByType;
    private final TaskItemRepository taskItemRepository;
    private final TaskRepository taskRepository;
    private final AudioStorageRepository audioStorageRepository;
    private final ConverterImage converterImage;

    public void resolve(List<ResultExerciseDTO> exercisesDTO) {
        List<TaskItem> taskItemsResolved = exercisesDTO
                .stream()
                .parallel()
                .map(this::resolveExercise)
                .collect(Collectors.toList());

        List<Task> tasksCompleted = taskItemsResolved
                .stream()
                .map(TaskItem::getTask)
                .collect(Collectors.toList());

        this.taskItemRepository.saveAll(taskItemsResolved);
        this.taskRepository.saveAll(tasksCompleted);
    }

    private TaskItem resolveExercise(ResultExerciseDTO resultExerciseDTO) {
        return taskItemRepository
                .findById(resultExerciseDTO.getIdTaskItem())
                .map(taskItem -> {
                    taskItem.getTask().setStatus(TaskStatus.DONE);
                    try {
                        TypeExercise type = taskItem.getExercise().getType();
                        return resolveStrategyByType.get(type).resolve(taskItem, resultExerciseDTO);
                    } catch (Exception e) {
                        log.error("Error in resolution exercises", e);
                        taskItem.setResult(ResultExercise.FAILURE.name());
                        return taskItem;
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("no puede haber taskitem vacio"));
    }

    public List<PhonemeDTO> getPhonemesResolvedByPatient(Long idPatient) {
        return this.taskRepository
                .findAllByPatientAndStatus(idPatient, TaskStatus.DONE)
                .stream()
                .map(task -> new PhonemeDTO(task.getPhoneme()))
                .distinct()
                .collect(Collectors.toList());
    }

    public ResolutionTaskDTO getExercisesResolvedByPatientAndPhoneme(Long idPatient, Long idPhoneme) {
        return this.taskRepository
                .findAllByPatientAndStatus(idPatient, TaskStatus.DONE)
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
                    String audioBase64 = "";
                    try {
                        if (taskItem.getExercise().getType() == TypeExercise.SPEAK) {
                            Resource resource = this.audioStorageRepository.getAudioByFilename(taskItem.getUrlAudio());
                            audioBase64 = this.converterImage.getEncoded(resource.getInputStream().readAllBytes());
                        }
                    } catch (IOException e) {
                        audioBase64 = "";
                    }
                    return new TaskItemDTO(taskItem.getIdTask(), taskItem.getResult(), taskItem.getExercise().getType(), audioBase64);
                })
                .collect(Collectors.toList());
    }

    private ResolutionTaskDTO converterToResolutionTaskDTO(Phoneme p, List<Task> tasks) {
        List<TaskDTO> tasksDTO = tasks
                .stream()
                .map(task -> new TaskDTO(task.getIdTaskGroup(), task.getLevel(), task.getCategory()))
                .collect(Collectors.toList());
        return new ResolutionTaskDTO(p.getIdPhoneme(), p.getNamePhoneme(), tasksDTO);
    }
}

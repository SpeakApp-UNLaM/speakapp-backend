package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.storages.database.models.TaskItem;
import com.guba.spring.speakappbackend.storages.database.repositories.TaskItemRepository;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.services.strategies.ResolveStrategy;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResolveExerciseService {

    private final Map<TypeExercise, ResolveStrategy> resolveStrategyByType;
    private final TaskItemRepository taskItemRepository;

    public void resolve(List<ResultExerciseDTO> exercisesDTO) {
        List<TaskItem> taskItemsResolved = exercisesDTO
                .stream()
                .map(this::resolveExercise)
                .collect(Collectors.toList());

        this.taskItemRepository.saveAll(taskItemsResolved);
    }

    private TaskItem resolveExercise(ResultExerciseDTO resultExerciseDTO) {
        return taskItemRepository
                .findById(resultExerciseDTO.getIdTaskItem())
                .map( taskItem -> {
                    TypeExercise type = taskItem.getExercise().getType();
                    return resolveStrategyByType.get(type).resolve(taskItem, resultExerciseDTO);
                })
                .orElseThrow(() -> new IllegalArgumentException("no puede haber taskitem vacio"));
    }
}

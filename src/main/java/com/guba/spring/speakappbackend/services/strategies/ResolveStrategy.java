package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.repositories.database.models.TaskItem;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;

public interface ResolveStrategy {
    TaskItem resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO);
}

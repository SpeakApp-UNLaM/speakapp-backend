package com.guba.spring.speakappbackend.services.strategies;

import com.guba.spring.speakappbackend.database.models.TaskItem;
import com.guba.spring.speakappbackend.web.schemas.ResultExerciseDTO;

public interface ResolveStrategy {
    String resolve(TaskItem taskItem, ResultExerciseDTO resultExerciseDTO);
}

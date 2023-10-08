package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.enums.TypeExercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskItemDTO {
    private Long idTaskItem;
    private String result;
    private TypeExercise type;
}
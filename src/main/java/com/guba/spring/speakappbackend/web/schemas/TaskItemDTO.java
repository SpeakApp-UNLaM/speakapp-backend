package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.enums.ResultExercise;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskItemDTO {
    private Long idTaskItem;
    private ResultExercise result;
    private TypeExercise type;
    private String audio;
    private String resultExpected;
}
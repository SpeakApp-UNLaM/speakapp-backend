package com.guba.spring.speakappbackend.schemas;

import com.guba.spring.speakappbackend.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateExerciseRequest {
    private long phonemeId;
    private int level;
    private Set<Category> categories;
}

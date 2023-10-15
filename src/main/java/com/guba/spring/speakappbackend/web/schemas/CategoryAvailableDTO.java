package com.guba.spring.speakappbackend.web.schemas;

import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAvailableDTO {
    private Category category;
    private Set<TypeExercise> typeExercises;
    private Set<Integer> levels;
}
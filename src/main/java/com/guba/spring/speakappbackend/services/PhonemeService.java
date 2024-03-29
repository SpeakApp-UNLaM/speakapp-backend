package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.storages.database.repositories.ExerciseRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.PhonemeRepository;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.web.schemas.CategoryAvailableDTO;
import com.guba.spring.speakappbackend.web.schemas.PhonemeDTO;
import com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO.CategoryDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhonemeService {

    private final PhonemeRepository phonemeRepository;
    private final ExerciseRepository exerciseRepository;

    public List<PhonemeDTO> getAll() {
        return phonemeRepository
                .findAll()
                .stream()
                .map(PhonemeDTO::new)
                .collect(Collectors.toList());
    }

    public List<PhonemeDTO> getAllPhonemeAvailable() {
        return exerciseRepository
                .findAllPhonemeDistinct()
                .stream()
                .map(PhonemeDTO::new)
                .collect(Collectors.toList());
    }

    public PhonemeCategoryDTO getById(Long idPhoneme) {
        return this.phonemeRepository
                .findById(idPhoneme)
                .map(p -> {
                    var categoriesDTOS = this.exerciseRepository
                            .findAllByPhoneme(idPhoneme)
                            .stream()
                            .map(e -> CategoryDTO
                                    .builder()
                                    .category(e.getCategory())
                                    .level(e.getLevel())
                                    .build())
                            .collect(Collectors.toSet());
                    return new PhonemeCategoryDTO(p, categoriesDTOS);
                })
                .orElse(new PhonemeCategoryDTO());
    }

    public List<CategoryAvailableDTO> getCategoryAvailableByIdPhoneme(Long idPhoneme) {
        return this.exerciseRepository
                .findAllByPhonemeGroupByCategoryAndLevelAndType(idPhoneme)
                .stream()
                .collect(Collectors.groupingBy(
                        e -> (Category) e[0],
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    var category = entry.getKey();
                    var levels = new HashSet<Integer>();
                    var types = new HashSet<TypeExercise>();
                    entry
                            .getValue()
                            .forEach(v -> {
                                levels.add((Integer) v[1]);
                                types.add((TypeExercise) v[2]);
                            });
                    return new CategoryAvailableDTO(category, types, levels);
                })
                .collect(Collectors.toList());
    }

    
}

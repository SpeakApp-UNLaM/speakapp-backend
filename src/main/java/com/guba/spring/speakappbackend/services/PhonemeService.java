package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.storages.database.repositories.ExerciseRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.PhonemeRepository;
import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.web.schemas.PhonemeDTO;
import com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                .orElseThrow( () -> new NotFoundElementException("Not found Phoneme for the id " + idPhoneme));
    }
}

package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.storages.database.models.Articulation;
import com.guba.spring.speakappbackend.storages.database.repositories.ArticulationRepository;
import com.guba.spring.speakappbackend.web.schemas.ArticulationDTO;
import com.guba.spring.speakappbackend.web.schemas.PhonemeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticulationService {

    private final ArticulationRepository articulationRepository;
    public List<PhonemeDTO> getArticulationsAvailable() {
        return this.articulationRepository
                .findAll()
                .stream()
                .map(Articulation::getPhoneme)
                .map(PhonemeDTO::new)
                .sorted(Comparator.comparing(PhonemeDTO::getNamePhoneme))
                .collect(Collectors.toList());
    }

    public ArticulationDTO getArticulation(Long idPhoneme) {
        return this.articulationRepository
                .findByIdPhoneme(idPhoneme)
                .map(a -> new ArticulationDTO(a.getIdArticulation(), a.getPhoneme().getNamePhoneme(), a.getImageData()))
                .orElseThrow(() -> new NotFoundElementException("No exist Articulation for id phoneme " + idPhoneme));
    }
}

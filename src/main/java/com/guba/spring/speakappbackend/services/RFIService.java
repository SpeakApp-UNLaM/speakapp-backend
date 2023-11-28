package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.storages.database.models.RFIResolution;
import com.guba.spring.speakappbackend.storages.database.repositories.RFIRepository;
import com.guba.spring.speakappbackend.storages.database.repositories.RFIResolutionRepository;
import com.guba.spring.speakappbackend.web.schemas.RegisterPhonologicalInducedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class RFIService {

    private final RFIRepository rfiRepository;
    private final RFIResolutionRepository rfiResolutionRepository;

    public List<RegisterPhonologicalInducedDTO> getRFI() {
        return rfiRepository
                .findAll()
                .stream()
                .map(rfi -> new RegisterPhonologicalInducedDTO(
                        rfi.getIdRfi(),
                        rfi.getImage().getImageData(),
                        rfi.getImage().getName(),
                        null))
                .sorted(Comparator.comparing(RegisterPhonologicalInducedDTO::getIdRfi))
                .collect(Collectors.toList());
    }

    public List<RegisterPhonologicalInducedDTO> getResolvedRFI(Long idPatient) {
        return this.rfiResolutionRepository
                .findRFIResolutionsByIdPatient(idPatient)
                .stream()
                .map(rfiResolution -> new RegisterPhonologicalInducedDTO(
                        rfiResolution.getIdRfi(),
                        "",
                        rfiResolution.getRfi().getImage().getName(),
                        rfiResolution.getStatusResolution()))
                .collect(Collectors.toList());
    }

    public List<RegisterPhonologicalInducedDTO> resolveRFI(Long idPatient, List<RegisterPhonologicalInducedDTO> rfiDTOs) {
        this.rfiResolutionRepository.deleteRFIResolutionsByIdPatient(idPatient);
        this.rfiResolutionRepository
                .saveAll(rfiDTOs
                        .stream()
                        .map(rfiDTO -> new RFIResolution(
                                rfiDTO.getIdRfi(),
                                idPatient,
                                rfiDTO.getStatus()
                        )).collect(Collectors.toList()))
                ;
        return rfiDTOs;
    }


}

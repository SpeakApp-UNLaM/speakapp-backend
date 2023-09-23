package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.web.schemas.ProfessionalDTO;
import com.guba.spring.speakappbackend.services.ProfessionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "professionals")
@Slf4j
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PutMapping
    public ResponseEntity<ProfessionalDTO> updateProfessional(@RequestBody ProfessionalDTO professionalDTO) {

        ProfessionalDTO patientDTOSaved = this.professionalService.updateProfessionalById(professionalDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDTOSaved);
    }

    @GetMapping
    public ResponseEntity<Set<ProfessionalDTO>> getProfessionalAll() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.professionalService.getProfessionalAll());
    }

    @GetMapping(value = "/{codeOrIdProfessional}")
    public ResponseEntity<ProfessionalDTO> getProfessional(@PathVariable String codeOrIdProfessional) {

        boolean isNumeric = codeOrIdProfessional.matches("-?\\d+(\\.\\d+)?");
        ProfessionalDTO patientDTO;
        if (isNumeric) {
            patientDTO = this.professionalService.getProfessionalById(Long.valueOf(codeOrIdProfessional));
        } else {
            patientDTO = this.professionalService.getProfessionalByCode(codeOrIdProfessional);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDTO);
    }

    @DeleteMapping(value = "/{idProfessional}")
    public ResponseEntity<Void> removeProfessional(@PathVariable Long idProfessional) {

        this.professionalService.removeProfessional(idProfessional);
        return ResponseEntity
                .noContent()
                .build();
    }
}

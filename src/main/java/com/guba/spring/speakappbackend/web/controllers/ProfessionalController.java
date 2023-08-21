package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.web.schemas.ProfessionalDTO;
import com.guba.spring.speakappbackend.services.ProfessionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "professionals")
@Slf4j
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PutMapping
    public ResponseEntity<ProfessionalDTO> updatePatient(@RequestBody ProfessionalDTO professionalDTO) {

        ProfessionalDTO patientDTOSaved = this.professionalService.updateProfessional(professionalDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDTOSaved);
    }

    @GetMapping(value = "/{idProfessional}")
    public ResponseEntity<ProfessionalDTO> getPatient(@PathVariable Long idProfessional) {

        ProfessionalDTO patientDTOSaved = this.professionalService.getProfessional(idProfessional);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDTOSaved);
    }

    @DeleteMapping(value = "/{idProfessional}")
    public ResponseEntity<Void> removePatient(@PathVariable Long idProfessional) {

        this.professionalService.removeProfessional(idProfessional);
        return ResponseEntity
                .noContent()
                .build();
    }
}

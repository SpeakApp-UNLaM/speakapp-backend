package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.PatientService;
import com.guba.spring.speakappbackend.web.schemas.PatientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "patients")
@Slf4j
public class PatientController {

    private final PatientService patientService;


    @PutMapping
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) {

        PatientDTO patientDTOSaved = this.patientService.updatePatient(patientDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDTOSaved);
    }

    @GetMapping(value = "/{idPatient}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long idPatient) {

        PatientDTO patientDTOSaved = this.patientService.getPatient(idPatient);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDTOSaved);
    }

    @DeleteMapping(value = "/{idPatient}")
    public ResponseEntity<Void> removePatient(@PathVariable Long idPatient) {

        this.patientService.removePatient(idPatient);
        return ResponseEntity
                .noContent()
                .build();
    }
}

package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.RFIService;
import com.guba.spring.speakappbackend.web.schemas.RegisterPhonologicalInducedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "rfi")
@Slf4j
public class RFIController {

    private final RFIService rfiService;

    @GetMapping
    public ResponseEntity<List<RegisterPhonologicalInducedDTO>> getRFI() {
        var response = rfiService.getRFI();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{idPatient}")
    public ResponseEntity<List<RegisterPhonologicalInducedDTO>> resolveRFI(@PathVariable Long idPatient) {
        var response = rfiService.getResolvedRFI(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/{idPatient}")
    public ResponseEntity<List<RegisterPhonologicalInducedDTO>> resolveRFI(
            @PathVariable Long idPatient,
            @RequestBody List<RegisterPhonologicalInducedDTO> rfiDTOs
            ) {
        var response = rfiService.resolveRFI(idPatient, rfiDTOs);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

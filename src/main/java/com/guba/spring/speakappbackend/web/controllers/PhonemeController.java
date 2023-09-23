package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.PhonemeService;
import com.guba.spring.speakappbackend.web.schemas.PhonemeDTO;
import com.guba.spring.speakappbackend.web.schemas.PhonemeCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "phonemes")
@Slf4j
public class PhonemeController {

    private final PhonemeService phonemeService;

    @GetMapping(path = "{idPhoneme}")
    public ResponseEntity<PhonemeCategoryDTO> getById(@PathVariable Long idPhoneme) {
        var response = phonemeService.getById(idPhoneme);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PhonemeDTO>> getAll() {
        var response = phonemeService.getAll();
        return ResponseEntity.ok(response);
    }
}

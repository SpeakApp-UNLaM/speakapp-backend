package com.guba.spring.speakappbackend.controllers;

import com.guba.spring.speakappbackend.schemas.CareerDTO;
import com.guba.spring.speakappbackend.services.CareerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class CareerController {

    private final CareerService careerService;

    @Autowired
    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping("/careers")
    public ResponseEntity<List<CareerDTO>> getCareers() {
        log.info("Obtener todos los care");
        List<CareerDTO> careers = this.careerService.getCareers();
        return ResponseEntity.ok(careers);
    }

    @GetMapping("/careers/{name}")
    public ResponseEntity<List<CareerDTO>> getCareersByName(@PathVariable String name) {
        log.info("Obtener todos los care");
        List<CareerDTO> careers = this.careerService.getCareersByName(name);
        return ResponseEntity.ok(careers);
    }

    @PostMapping("/careers")
    public ResponseEntity<CareerDTO> createCareer(@RequestBody CareerDTO careerDTO){
        log.info("Obtener todos los care");
        CareerDTO career = this.careerService.saveCareer(careerDTO);
        return ResponseEntity.ok(career);
    }
}

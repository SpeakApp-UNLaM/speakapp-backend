package com.guba.spring.speakappbackend.controllers;

import com.guba.spring.speakappbackend.schemas.CareerDTO;
import com.guba.spring.speakappbackend.schemas.PendingDTO;
import com.guba.spring.speakappbackend.services.CareerService;
import com.guba.spring.speakappbackend.services.PendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PendingController {

    private final PendingService pendingService;

    @Autowired
    public PendingController(PendingService pendingService) {
        this.pendingService = pendingService;
    }
    @GetMapping("/pendings")
    public ResponseEntity<List<PendingDTO>> getPending() {
        log.info("Obtener todos los care");
        List<PendingDTO> pending = this.pendingService.getPending();
        return ResponseEntity.ok(pending);
    }
    @GetMapping("/pending/{name}")
    public ResponseEntity<List<PendingDTO>> getPendingByName(@PathVariable int name) {
        log.info("Obtener todos los care");
        List<PendingDTO> careers = this.pendingService.getPendingByName(name);
        return ResponseEntity.ok(careers);
    }

    @PostMapping("/pending")
    public ResponseEntity<PendingDTO> createPending(@RequestBody PendingDTO pendingDTO){
        log.info("Obtener todos los care");
        PendingDTO pending = this.pendingService.savePending(pendingDTO);
        return ResponseEntity.ok(pending);
    }
}

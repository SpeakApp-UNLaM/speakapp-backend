package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.ReportService;
import com.guba.spring.speakappbackend.web.schemas.ReportDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "reports")
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @PostMapping(path = "/{idPatient}")
    public ResponseEntity<ReportDTO> createReport(
            @PathVariable(name = "idPatient") Long idPatient,
            @RequestBody @Valid ReportDTO reportDTO) {
        log.info("create report, idPatient {}", idPatient);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.reportService.create(reportDTO, idPatient));
    }

    @GetMapping(path = "/{idPatient}")
    public ResponseEntity<List<ReportDTO>> getReports(
            @PathVariable(name = "idPatient") Long idPatient,
            @RequestParam(name = "limit", defaultValue = "40" ) int limit) {
        log.info("get reports of idPatient {}, limit {}", idPatient, limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.reportService.getReports(idPatient, limit));
    }

    @PutMapping(path = "/{idReport}")
    public ResponseEntity<ReportDTO> updateReports(
            @PathVariable(name = "idReport") Long idReport,
            @RequestBody @Valid ReportDTO reportDTO) {
        log.info("update reports of idReport {}", idReport);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.reportService.update(idReport, reportDTO));
    }

    @DeleteMapping(path = "/{idReport}")
    public ResponseEntity<List<ReportDTO>> deleteReport(@PathVariable(name = "idReport") Long idReport) {
        log.info("delete reports of idReport {}", idReport);
        this.reportService.deleteById(idReport);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}

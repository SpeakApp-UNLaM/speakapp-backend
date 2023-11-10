package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import com.guba.spring.speakappbackend.storages.database.models.Patient;
import com.guba.spring.speakappbackend.storages.database.models.Professional;
import com.guba.spring.speakappbackend.storages.database.models.Report;
import com.guba.spring.speakappbackend.storages.database.repositories.ReportRepository;
import com.guba.spring.speakappbackend.web.schemas.ReportDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final CustomUserDetailService customUserDetailService;

    public ReportDTO create(ReportDTO reportDTO, Long idPatient) {

        Professional professional = customUserDetailService.getUserCurrent(Professional.class);
        Patient patient = professional
                .getPatients()
                .stream()
                .filter(patientP -> patientP.getIdPatient().equals(idPatient))
                .findFirst()
                .orElseThrow(() -> new NotFoundElementException("The professional does not have patient " + idPatient));

        Report reportSaved =  this.reportRepository.save(new Report(professional, patient, LocalDateTime.now(), reportDTO.getTitle(), reportDTO.getBody()));
        return new ReportDTO(reportSaved);
    }

    public List<ReportDTO> getReports(Long idPatient, int limit) {
        Professional professional = customUserDetailService.getUserCurrent(Professional.class);
        Patient patient = professional
                .getPatients()
                .stream()
                .filter(patientP -> patientP.getIdPatient().equals(idPatient))
                .findFirst()
                .orElseThrow(() -> new NotFoundElementException("The professional "));

        return this.reportRepository
                .findAllByProfessionalAndPatient(professional, patient)
                .stream()
                .map(ReportDTO::new)
                .sorted(Comparator.comparing(ReportDTO::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public ReportDTO update(Long idReport, ReportDTO reportDTO) {
        return this.reportRepository
                .findById(idReport)
                .map(report -> {
                    report.setBody(reportDTO.getBody());
                    report.setTitle(reportDTO.getTitle());
                    report.setCreatedAt(LocalDateTime.now());
                    return report;
                })
                .map(this.reportRepository::save)
                .map(ReportDTO::new)
                .orElseThrow(() -> new NotFoundElementException("not found report id " + idReport))
                ;
    }

    public void deleteById(Long idReport) {
        this.reportRepository.deleteById(idReport);
    }
}

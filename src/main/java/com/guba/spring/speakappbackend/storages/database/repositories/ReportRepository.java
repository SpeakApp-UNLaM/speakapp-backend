package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.Patient;
import com.guba.spring.speakappbackend.storages.database.models.Professional;
import com.guba.spring.speakappbackend.storages.database.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByProfessionalAndPatient(Professional professional, Patient patient);

}
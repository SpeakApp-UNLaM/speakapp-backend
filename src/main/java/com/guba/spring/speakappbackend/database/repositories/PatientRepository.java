package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.database.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByUsernameOrEmail(String username, String email);
}
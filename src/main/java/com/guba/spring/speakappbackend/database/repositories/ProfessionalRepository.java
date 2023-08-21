package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.database.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    Professional findByUsernameOrEmail(String username, String email);

    Professional findByCode(String code);

}
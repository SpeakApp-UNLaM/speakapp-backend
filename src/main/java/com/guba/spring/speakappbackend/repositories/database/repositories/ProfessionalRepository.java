package com.guba.spring.speakappbackend.repositories.database.repositories;

import com.guba.spring.speakappbackend.repositories.database.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    Professional findByUsernameOrEmail(String username, String email);

    Optional<Professional> findByCode(String code);

}
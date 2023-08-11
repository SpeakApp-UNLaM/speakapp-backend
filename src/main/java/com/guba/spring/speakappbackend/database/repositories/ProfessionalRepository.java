package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}
package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.Phoneme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonemeRepository extends JpaRepository<Phoneme, Long> {

}
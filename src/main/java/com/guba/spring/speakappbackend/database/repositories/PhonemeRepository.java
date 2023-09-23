package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.database.models.Phoneme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonemeRepository extends JpaRepository<Phoneme, Long> {

}
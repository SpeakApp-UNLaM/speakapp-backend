package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.Articulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticulationRepository extends JpaRepository<Articulation, Long> {

    Optional<Articulation> findByIdPhoneme(Long idPhoneme);
}

package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.RFIResolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RFIResolutionRepository extends JpaRepository<RFIResolution, Long> {

    List<RFIResolution> findAllByIdPatient(Long idPatient);
}

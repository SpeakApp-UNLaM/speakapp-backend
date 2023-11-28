package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.RFIResolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface RFIResolutionRepository extends JpaRepository<RFIResolution, Long> {

    List<RFIResolution> findRFIResolutionsByIdPatient(Long idPatient);

    @Transactional
    @Modifying
    void deleteRFIResolutionsByIdPatient(Long idPatient);

}

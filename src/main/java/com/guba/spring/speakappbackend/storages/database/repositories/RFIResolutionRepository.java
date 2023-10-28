package com.guba.spring.speakappbackend.storages.database.repositories;

import com.guba.spring.speakappbackend.storages.database.models.RFIResolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RFIResolutionRepository extends JpaRepository<RFIResolution, Long> {

    @Query(value = "SELECT r FROM RFIResolution r where r.idPatient = :idPatient")
    List<RFIResolution> findAllByIdPatient(@Param("idPatient") Long idPatient);

}

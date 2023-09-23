package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.database.models.Task;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("Select t From Task t " +
            "Where t.patient.idPatient = :idPatient " +
            "and t.phoneme.idPhoneme = :idPhoneme " +
            "and t.status = :status " +
            "and :now BETWEEN t.startDate and t.endDate")
    List<Task> findAllByPatientAndPhonemeStatusAndBetween(@Param("idPatient") Long idPatient, @Param("idPhoneme") Long idPhoneme,  @Param("status") TaskStatus status, @Param("now") LocalDate now);

    @Query("Select t From Task t " +
            "Where t.patient.idPatient = :idPatient " +
            "and t.status = :status " +
            "and :now BETWEEN t.startDate and t.endDate")
    List<Task> findAllByPatientAndStatusAndBetween(@Param("idPatient") Long idPatient,  @Param("status") TaskStatus status, @Param("now") LocalDate now);

}
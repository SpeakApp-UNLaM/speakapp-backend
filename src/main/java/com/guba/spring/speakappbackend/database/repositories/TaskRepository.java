package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.database.models.Task;
import com.guba.spring.speakappbackend.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("Select t From Task t " +
            "Where t.patient.idPatient =: idPatient " +
            "and t.status =: status " +
            "and :now BETWEEN t.startDate and t.endDate")
    List<Task> findAllByPatientAndStatusAndBetween(Long idPatient, TaskStatus status, LocalDate now);

}
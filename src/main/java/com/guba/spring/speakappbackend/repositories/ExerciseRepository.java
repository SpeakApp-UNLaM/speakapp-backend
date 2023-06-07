package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.Exercise;
import com.guba.spring.speakappbackend.models.Pending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT c FROM Exercise c WHERE c.id = ?1")
    List<Exercise> getExerciseByName(int name);


}
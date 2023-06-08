package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.GroupExercise;
import com.guba.spring.speakappbackend.models.Pending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupExerciseRepository extends JpaRepository<GroupExercise, Long> {

    @Query("SELECT c FROM GroupExercise c WHERE c.wordGroupExercise = ?1")
    List<GroupExercise> getGroupExerciseByName(int name);
}
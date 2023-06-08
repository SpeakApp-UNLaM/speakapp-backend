package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.Career;
import com.guba.spring.speakappbackend.models.Pending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingRepository extends JpaRepository<Pending, Long> {

    @Query("SELECT c FROM Pending c WHERE c.idUsr = ?1")
    List<Pending> getPendingByName(int name);
    @Query("SELECT c,b.wordGroupExercise FROM Pending c INNER JOIN GroupExercise b ON b.id = c.idGroupExercise WHERE c.idUsr = ?1")
    List<Pending> getPendingByNameG(int name);

}
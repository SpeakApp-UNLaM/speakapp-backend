package com.guba.spring.speakappbackend.repositories;

import com.guba.spring.speakappbackend.models.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    @Query("SELECT c FROM Career c WHERE c.name = ?1")
    List<Career> getCareerByName(String name);
}
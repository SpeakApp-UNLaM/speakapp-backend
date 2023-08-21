package com.guba.spring.speakappbackend.database.repositories;

import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.database.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(value = "SELECT e FROM Exercise e JOIN e.phonemes p " +
            "WHERE e.category IN (:categories) AND e.level = :level AND p.idPhoneme = :idPhoneme")
    List<Exercise> findALLByCategoryAndLevelAndPhoneme(@Param("categories") Set<Category> categories, @Param("level") int level, @Param("idPhoneme") long idPhoneme);
}
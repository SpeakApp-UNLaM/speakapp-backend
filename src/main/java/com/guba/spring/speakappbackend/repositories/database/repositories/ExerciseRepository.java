package com.guba.spring.speakappbackend.repositories.database.repositories;

import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.repositories.database.models.Exercise;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(value = "SELECT e FROM Exercise e " +
            "WHERE e.category IN (:categories) AND e.level = :level AND e.phoneme.idPhoneme = :idPhoneme")
    List<Exercise> findAllByCategoriesAndLevelAndPhoneme(@Param("categories") Set<Category> categories, @Param("level") int level, @Param("idPhoneme") long idPhoneme);

    @Query(value = "SELECT e FROM Exercise e " +
            "WHERE e.category IN (:categories) AND e.phoneme.idPhoneme IN (:idPhonemes) AND e.type IN (:typesExercise)")
    List<Exercise> findAllByCategoriesAndPhonemesAndTypes(@Param("categories") Set<Category> categories, @Param("idPhonemes") Set<Long> idPhonemes, @Param("typesExercise") Set<TypeExercise> typesExercise);

    //@Query(value = "SELECT e FROM Exercise e JOIN e.phonemes p " +
    //        "WHERE p.idPhoneme = :idPhoneme")
    @Query(value = "SELECT e FROM Exercise e WHERE e.phoneme.idPhoneme = :idPhoneme")
    List<Exercise> findAllByPhoneme(@Param("idPhoneme") long idPhoneme);

}
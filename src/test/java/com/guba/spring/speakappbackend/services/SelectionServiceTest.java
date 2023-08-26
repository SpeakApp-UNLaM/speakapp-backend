package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.database.models.Exercise;
import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SelectionServiceTest {

    private final SelectionService selectionService = new SelectionService();

    @BeforeEach
    void init() {
        selectionService.setCountExercise(5);
    }

    @RepeatedTest(10_000)
    void selectionExercises() {
        List<Exercise> exercises = new ArrayList<>(List.of(
                new Exercise(1L, "", 1, Category.WORD, TypeExercise.SPEAK, Set.of(), Set.of()),
                new Exercise(2L, "", 1, Category.WORD, TypeExercise.SPEAK, Set.of(), Set.of()),
                new Exercise(3L, "", 1, Category.WORD, TypeExercise.SPEAK, Set.of(), Set.of()),
                new Exercise(4L, "", 1, Category.WORD, TypeExercise.SPEAK, Set.of(), Set.of()),
                new Exercise(5L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(6L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(7L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(8L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(9L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(10L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(11L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(12L, "", 1, Category.WORD, TypeExercise.CONSONANTAL_SYLLABLE, Set.of(), Set.of()),
                new Exercise(13L, "", 1, Category.WORD, TypeExercise.MULTIPLE_SELECTION, Set.of(), Set.of()),
                new Exercise(14L, "", 1, Category.WORD, TypeExercise.MULTIPLE_SELECTION, Set.of(), Set.of()),
                new Exercise(15L, "", 1, Category.WORD, TypeExercise.MULTIPLE_SELECTION, Set.of(), Set.of()),
                new Exercise(16L, "", 1, Category.WORD, TypeExercise.MULTIPLE_SELECTION, Set.of(), Set.of()),
                new Exercise(17L, "", 1, Category.WORD, TypeExercise.MULTIPLE_SELECTION, Set.of(), Set.of()),
                new Exercise(18L, "", 1, Category.WORD, TypeExercise.MULTIPLE_SELECTION, Set.of(), Set.of()),
                new Exercise(19L, "", 1, Category.WORD, TypeExercise.MULTIPLE_SELECTION, Set.of(), Set.of()),
                new Exercise(20L, "", 1, Category.WORD, TypeExercise.MULTIPLE_MATCH_SELECTION, Set.of(), Set.of()),
                new Exercise(21L, "", 1, Category.WORD, TypeExercise.MULTIPLE_MATCH_SELECTION, Set.of(), Set.of()),
                new Exercise(22L, "", 1, Category.WORD, TypeExercise.MULTIPLE_MATCH_SELECTION, Set.of(), Set.of()),
                new Exercise(23L, "", 1, Category.WORD, TypeExercise.MULTIPLE_MATCH_SELECTION, Set.of(), Set.of()),
                new Exercise(24L, "", 1, Category.WORD, TypeExercise.MULTIPLE_MATCH_SELECTION, Set.of(), Set.of()),
                new Exercise(25L, "", 1, Category.WORD, TypeExercise.MULTIPLE_MATCH_SELECTION, Set.of(), Set.of()),
                new Exercise(26L, "", 1, Category.WORD, TypeExercise.MULTIPLE_MATCH_SELECTION, Set.of(), Set.of()),
                new Exercise(27L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_SYLLABLE, Set.of(), Set.of()),
                new Exercise(28L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_SYLLABLE, Set.of(), Set.of()),
                new Exercise(29L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_SYLLABLE, Set.of(), Set.of()),
                new Exercise(30L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_SYLLABLE, Set.of(), Set.of()),
                new Exercise(31L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_SYLLABLE, Set.of(), Set.of()),
                new Exercise(32L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_SYLLABLE, Set.of(), Set.of()),
                new Exercise(33L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_WORD, Set.of(), Set.of()),
                new Exercise(34L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_WORD, Set.of(), Set.of()),
                new Exercise(35L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_WORD, Set.of(), Set.of()),
                new Exercise(36L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_WORD, Set.of(), Set.of()),
                new Exercise(37L, "", 1, Category.WORD, TypeExercise.SINGLE_SELECTION_WORD, Set.of(), Set.of()),
                new Exercise(38L, "", 1, Category.WORD, TypeExercise.ORDER_SYLLABLE, Set.of(), Set.of()),
                new Exercise(39L, "", 1, Category.WORD, TypeExercise.ORDER_SYLLABLE, Set.of(), Set.of()),
                new Exercise(40L, "", 1, Category.WORD, TypeExercise.ORDER_SYLLABLE, Set.of(), Set.of()),
                new Exercise(41L, "", 1, Category.WORD, TypeExercise.ORDER_SYLLABLE, Set.of(), Set.of()),
                new Exercise(42L, "", 1, Category.WORD, TypeExercise.ORDER_SYLLABLE, Set.of(), Set.of()),
                new Exercise(43L, "", 1, Category.WORD, TypeExercise.ORDER_SYLLABLE, Set.of(), Set.of()),
                new Exercise(44L, "", 1, Category.WORD, TypeExercise.MINIMUM_PAIRS_SELECTION, Set.of(), Set.of()),
                new Exercise(45L, "", 1, Category.WORD, TypeExercise.MINIMUM_PAIRS_SELECTION, Set.of(), Set.of()),
                new Exercise(46L, "", 1, Category.WORD, TypeExercise.MINIMUM_PAIRS_SELECTION, Set.of(), Set.of()),
                new Exercise(47L, "", 1, Category.WORD, TypeExercise.MINIMUM_PAIRS_SELECTION, Set.of(), Set.of()),
                new Exercise(48L, "", 1, Category.WORD, TypeExercise.MINIMUM_PAIRS_SELECTION, Set.of(), Set.of()),
                new Exercise(49L, "", 1, Category.WORD, TypeExercise.MINIMUM_PAIRS_SELECTION, Set.of(), Set.of())
        ));
        Collections.shuffle(exercises);

        var result = selectionService.selectionExercises(exercises);
        long countExerciseSpeak = result.stream().filter(e -> e.getType() == TypeExercise.SPEAK).count();
        assertEquals(5, result.size());
        assertEquals(3, countExerciseSpeak);
    }
}
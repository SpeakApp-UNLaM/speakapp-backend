package com.guba.spring.speakappbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TypeExercise {

    SPEAK("speak"),
    MULTIPLE_MATCH_SELECTION("multiple_match_selection"),
    MINIMUM_PAIRS_SELECTION("minimum_pairs_selection"),
    MULTIPLE_SELECTION("multiple_selection"),
    SINGLE_SELECTION_SYLLABLE("single_selection_syllable"),
    ORDER_SYLLABLE("order_syllable"),
    SINGLE_SELECTION_WORD("single_selection_word"),
    CONSONANTAL_SYLLABLE("consonantal_syllable"),
    ORDER_WORD("order_word")
    ;

    private static final Map<String, TypeExercise> TYPE_EXERCISE_BY_NAME = Arrays
            .stream(TypeExercise.values())
            .collect(Collectors.toMap(TypeExercise::getName, Function.identity()));

    private final String name;

    TypeExercise(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static TypeExercise getTypeExercise(String name) {
        return TYPE_EXERCISE_BY_NAME.get(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

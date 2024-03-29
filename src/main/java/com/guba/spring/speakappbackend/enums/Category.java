package com.guba.spring.speakappbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Category {
    SYLLABLE("syllable"),
    WORD("word"),
    PHRASE("phrase");

    private static final Map<String, Category> CATEGORY_BY_NAME = Arrays
            .stream(Category.values())
            .collect(Collectors.toMap(Category::getName, Function.identity()));

    private final String name;

    Category(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Category getCategory(String name) {
        return CATEGORY_BY_NAME.get(name);
    }

    @Override
    public String toString() {
        return name;
    }

}

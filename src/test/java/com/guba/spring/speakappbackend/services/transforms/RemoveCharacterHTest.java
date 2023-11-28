package com.guba.spring.speakappbackend.services.transforms;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveCharacterHTest {

    private final RemoveCharacterHDecorator removeCharacterHDecorator = new RemoveCharacterHDecorator(new TransformLowerCase());

    @ParameterizedTest
    @CsvFileSource(resources = {"/cases/exercises_with_h.csv"})
    void transform(String input, String expected) {
        assertEquals(expected, removeCharacterHDecorator.transform(input));
    }
}

package com.guba.spring.speakappbackend.services.transforms;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class ReplaceAccentDecoratorTest {

    private final ReplaceAccentDecorator replaceAccentDecorator = new ReplaceAccentDecorator(new TransformLowerCase());

    @ParameterizedTest
    @CsvSource({
            "bárráéíóúÁÉÍÓÚ, barraeiouaeiou"
    })
    void transform(String input, String expected) {
        assertEquals(expected, replaceAccentDecorator.transform(input));
    }
}
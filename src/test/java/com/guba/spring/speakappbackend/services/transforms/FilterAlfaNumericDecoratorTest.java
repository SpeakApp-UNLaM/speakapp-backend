package com.guba.spring.speakappbackend.services.transforms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class FilterAlfaNumericDecoratorTest {

    private final FilterAlfaNumericDecorator filterAlfaNumericDecorator = new FilterAlfaNumericDecorator(new TransformLowerCase());

    @ParameterizedTest
    @CsvSource({
            "arbol, arbol",
            "barra, barra",
            "BArra, barra",
            "BÁrra, bárra",
            "bárráéíóúÁÉÍÓÚ, bárráéíóúáéíóú",
            "bárráéíóú      ÁÉÍÓÚ, bárráéíóú      áéíóú",
            "barra@gmail.com, barragmailcom"
    })
    void transform(String input, String expected) {
        assertEquals(expected, filterAlfaNumericDecorator.transform(input));
    }
}
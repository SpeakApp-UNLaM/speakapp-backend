package com.guba.spring.speakappbackend.web.converters;

import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.exceptions.ConverterEnumException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TypeExerciseEnumConverter implements Converter<String, TypeExercise> {
    @Override
    public TypeExercise convert(String source) {
        return Optional
                .of(source)
                .map(String::toLowerCase)
                .map(TypeExercise::getTypeExercise)
                .orElseThrow(() -> new ConverterEnumException("the parameter " + source + " is not valid"));
    }
}
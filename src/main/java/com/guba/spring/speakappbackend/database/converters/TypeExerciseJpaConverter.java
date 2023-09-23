package com.guba.spring.speakappbackend.database.converters;

import com.guba.spring.speakappbackend.enums.TypeExercise;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class TypeExerciseJpaConverter implements AttributeConverter<TypeExercise, String> {
 
   @Override
   public String convertToDatabaseColumn(TypeExercise category) {
       return Optional.of(category)
               .map(TypeExercise::getName)
               .orElseThrow(IllegalArgumentException::new);
   }
 
   @Override
   public TypeExercise convertToEntityAttribute(String code) {
       return Optional.of(code)
               .map(TypeExercise::getTypeExercise)
               .orElseThrow(IllegalArgumentException::new);
   }

}
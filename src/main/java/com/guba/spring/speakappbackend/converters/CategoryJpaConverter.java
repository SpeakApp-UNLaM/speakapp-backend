package com.guba.spring.speakappbackend.converters;

import com.guba.spring.speakappbackend.enums.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class CategoryJpaConverter implements AttributeConverter<Category, String> {
 
   @Override
   public String convertToDatabaseColumn(Category category) {
       return Optional.of(category)
               .map(Category::getName)
               .orElse(null);
   }
 
   @Override
   public Category convertToEntityAttribute(String code) {
       return Optional.of(code)
               .map(Category::getCategory)
               .orElse(null);
   }
 
}
package com.guba.spring.speakappbackend.storages.database.converters;

import com.guba.spring.speakappbackend.enums.RoleEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class RoleJpaConverter implements AttributeConverter<RoleEnum, String> {
 
   @Override
   public String convertToDatabaseColumn(RoleEnum category) {
       return Optional.of(category)
               .map(RoleEnum::getName)
               .orElseThrow(IllegalArgumentException::new);
   }
 
   @Override
   public RoleEnum convertToEntityAttribute(String code) {
       return Optional.of(code)
               .map(RoleEnum::getRole)
               .orElseThrow(IllegalArgumentException::new);
   }

}
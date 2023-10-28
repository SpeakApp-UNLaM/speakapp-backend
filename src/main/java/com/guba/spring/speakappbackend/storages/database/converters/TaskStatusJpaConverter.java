package com.guba.spring.speakappbackend.storages.database.converters;

import com.guba.spring.speakappbackend.enums.TaskStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class TaskStatusJpaConverter implements AttributeConverter<TaskStatus, String> {
 
   @Override
   public String convertToDatabaseColumn(TaskStatus taskStatus) {
       return Optional.of(taskStatus)
               .map(TaskStatus::getName)
               .orElseThrow(IllegalArgumentException::new);
   }
 
   @Override
   public TaskStatus convertToEntityAttribute(String code) {
       return Optional.of(code)
               .map(TaskStatus::getTaskStatus)
               .orElseThrow(IllegalArgumentException::new);
   }

}
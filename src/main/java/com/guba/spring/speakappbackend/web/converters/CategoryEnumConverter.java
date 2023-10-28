package com.guba.spring.speakappbackend.web.converters;

import com.guba.spring.speakappbackend.exceptions.ConverterEnumException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.guba.spring.speakappbackend.enums.Category;

import java.util.Optional;

@Component
public class CategoryEnumConverter implements Converter<String, Category> {
    @Override
    public Category convert(String source) {
        return Optional
                .of(source)
                .map(String::toLowerCase)
                .map(Category::getCategory)
                .orElseThrow(() -> new ConverterEnumException("the parameter " + source + " is not valid"));
    }
}
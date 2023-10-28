package com.guba.spring.speakappbackend.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


@Component
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final List<Converter> converters;
    @Override
    public void addFormatters(FormatterRegistry registry) {
        converters.forEach(registry::addConverter);
    }
}

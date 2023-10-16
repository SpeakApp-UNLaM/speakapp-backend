package com.guba.spring.speakappbackend.configs;

import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.services.strategies.*;
import com.guba.spring.speakappbackend.services.transforms.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class SpeakAppConfig {

    @Bean
    Map<TypeExercise, ResolveStrategy> getResolveStrategyByType(List<ResolveStrategy> resolveStrategies) {
        var classResolverByTypeExercise = Map.of(
                TypeExercise.SPEAK, ResolveSpeak.class,
                TypeExercise.MULTIPLE_MATCH_SELECTION, ResolveWordMatchSelection.class,
                TypeExercise.MINIMUM_PAIRS_SELECTION, ResolveWordMatchSelection.class,
                TypeExercise.MULTIPLE_SELECTION, ResolveSyllableMatchSelection.class,
                TypeExercise.SINGLE_SELECTION_SYLLABLE, ResolveSyllableMatchSelection.class,
                TypeExercise.SINGLE_SELECTION_WORD, ResolveSingleSelection.class,
                TypeExercise.ORDER_SYLLABLE, ResolveOrder.class,
                TypeExercise.CONSONANTAL_SYLLABLE, ResolveConsonantalSyllable.class
                );

        return classResolverByTypeExercise
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> resolveStrategies
                                .stream()
                                .filter(resolveStrategy -> e.getValue().equals(resolveStrategy.getClass()))
                                .findFirst()
                                .orElseThrow(IllegalArgumentException::new)
                ));
    }

    @Bean
    @Qualifier("transformLowerCase")
    TransformerText transformLowerCase() { // Injected automatically
        return new TransformLowerCase();
    }

    @Bean
    @Qualifier("removeComaTransformerDecorator")
    TransformerTextDecorator removeComaTransformerDecorator(@Qualifier("transformLowerCase") final TransformerText transformerText) {
        return new FilterAlfaNumericDecorator(transformerText);
    }

    @Bean
    @Qualifier("removeTransformerDecorator")
    TransformerTextDecorator removeTransformerDecorator(@Qualifier("removeComaTransformerDecorator") final TransformerText transformerText) {
        return new ReplaceAccentDecorator(transformerText);
    }

}

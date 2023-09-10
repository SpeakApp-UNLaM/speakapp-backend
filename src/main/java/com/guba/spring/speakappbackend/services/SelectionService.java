package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.database.models.Exercise;
import com.guba.spring.speakappbackend.enums.Category;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@ConfigurationProperties("speak-backend")
@Setter
@Getter
public class SelectionService {

    private int countExercise;

    public List<Exercise> selectionExercisesByPhonemeAndLevelAndCategory(List<Exercise> exercises) {
        return exercises
                .stream()
                .collect(Collectors.groupingBy(
                        this::makeGrouping,
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> selectionExercises(entry.getValue()).stream())
                .collect(Collectors.toList());
    }

    private Aggregation makeGrouping(Exercise e) {
        String phoneme =  e
                .getPhoneme()
                .getNamePhoneme();
        return new Aggregation(phoneme, e.getLevel(), e.getCategory());
    }

    public List<Exercise> selectionExercises(List<Exercise> exercises) {
        long seed = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        int countExercise = Math.min(exercises.size(), getCountExercise());
        int countExerciseTypeSpeak = (int) Math.ceil(countExercise / 2f);
        int countExerciseTypeOther = countExercise - countExerciseTypeSpeak;

        List<Exercise> exercisesTypeSpeak = exercises
                .stream()
                .filter(e-> e.getType() == TypeExercise.SPEAK)
                .collect(Collectors.toList());

        List<Exercise> exercisesTypeOther = exercises
                .stream().filter(e-> e.getType() != TypeExercise.SPEAK)
                .collect(Collectors.toList());

        Set<Exercise> selectionExercisesTypeSpeak = getExercisesRandom(seed, countExerciseTypeSpeak, exercisesTypeSpeak);

        Set<Exercise> selectionExercisesTypeOther = getExercisesRandom(seed, countExerciseTypeOther, exercisesTypeOther);

        return Stream
                .concat(selectionExercisesTypeSpeak.stream(), selectionExercisesTypeOther.stream())
                .collect(Collectors.toList());
    }

    private Set<Exercise> getExercisesRandom(long seed, int limit, List<Exercise> exercises) {
        return new Random(seed)
                .ints(0, exercises.size())
                .distinct()
                .limit(limit)
                .mapToObj(exercises::get)
                .collect(Collectors.toSet());
    }

    @Builder
    @Data
    static class Aggregation {
        private String phoneme;
        private int level;
        private Category category;
    }
}

package com.guba.spring.speakappbackend.schedules;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.repositories.database.repositories.ExerciseRepository;
import com.guba.spring.speakappbackend.enums.TypeExercise;
import com.guba.spring.speakappbackend.services.FileStorageService;
import com.guba.spring.speakappbackend.services.ImageService;
import com.guba.spring.speakappbackend.services.strategies.ResolveStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessBatch {

    private final ClientWhisperApiCustom clientWhisperApiCustom;
    private final ImageService imageService;
    private final ExerciseRepository exerciseRepository;
    private final Map<TypeExercise, ResolveStrategy> resolveStrategyByType;
    private final FileStorageService fileStorageService;

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void callApiWhisper() throws FileNotFoundException {
        log.info("batch");
        //this.fileStorageService.save("hola".getBytes(), "12/folder-new", "hola.txt");
        //this.imageService.loadImagesToDatabase();
    }
}

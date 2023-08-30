package com.guba.spring.speakappbackend.schedules;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.services.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessBatch {

    private final ClientWhisperApiCustom clientWhisperApiCustom;
    private final ImageService imageService;

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void callApiWhisper() throws FileNotFoundException {
        this.imageService.loadImagesToDatabase();
    }
}

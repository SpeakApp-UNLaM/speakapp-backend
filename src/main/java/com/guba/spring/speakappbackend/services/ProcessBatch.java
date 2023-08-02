package com.guba.spring.speakappbackend.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProcessBatch {

    @Scheduled(initialDelay = 0, fixedDelayString = "2", timeUnit = TimeUnit.MINUTES)
    void callApiWhisper() {

    }
}

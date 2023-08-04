package com.guba.spring.speakappbackend.services;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessBatch {

    private final ClientWhisperApiCustom clientWhisperApiCustom;

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void callApiWhisper() {
        //this.clientWhisperApiCustom.getTranscription();
    }
}

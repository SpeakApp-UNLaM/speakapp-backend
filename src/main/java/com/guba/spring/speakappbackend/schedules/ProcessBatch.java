package com.guba.spring.speakappbackend.schedules;

import com.guba.spring.speakappbackend.clients.ClientWhisperApiCustom;
import com.guba.spring.speakappbackend.services.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessBatch {

    private final ClientWhisperApiCustom clientWhisperApiCustom;
    private final ImageService imageService;
    private final Environment env;

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void callApiWhisper() throws FileNotFoundException {
        log.info("batch");
        log.info("url whisper api {}", env.getProperty("speak-backend.client.whisper.url"));
        //this.imageService.loadImagesToDatabase();
        //this.imageService.loadArticulationToDatabase();
    }

    private void something() {
        var start = System.currentTimeMillis();
        Flux
                .fromIterable(List.of("guba", "reactor", "example"))
                //.parallel(4)
                .publishOn(Schedulers.newParallel("parallel-publish", 4))
                .flatMap(this::callService)
                .subscribeOn(Schedulers.newParallel("parallel-subcribe", 4))
                .subscribe(
                        ProcessBatch::onNext, // onNext
                        ProcessBatch::onError,  // onError
                        ProcessBatch::onComplete // onComplete
                );
        var end = System.currentTimeMillis();
        log.error("time total {} second", (end - start)/1000);
    }

    private Publisher<?> callService(String elem) {
        delay();
        var list = Arrays
                .stream(elem.split(""))
                .map(s -> new ResponseAny(s, s.length()))
                .collect(Collectors.toList());
        return Flux
                .fromIterable(list);
    }

    private static <T> void onNext(T data) {
        delay();
        log.info("onNext: Data received: " + data);
    }

    private static <T> void onError(Throwable err) {
        log.info("onError: Exception occurred: " + err.getMessage());
    }

    private static <T> void onComplete() {
        log.info("onComplete: Completed!");
    }

    private static void delay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("error thread", e);
        }
    }
    
    @Data
    static class ResponseAny {
        private final String consumer;
        private final int part;
    }
}

package com.guba.spring.speakappbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpeakApp {

    public static void main(String[] args) {
        log.debug("Startup app java SpeakApp");
        SpringApplication.run(SpeakApp.class, args);
    }
}

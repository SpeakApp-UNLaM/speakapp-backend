package com.guba.spring.speakappbackend;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class SpeakApp {

    public static void main(String[] args) {
        log.debug("Startup app java SpeakApp");
        SpringApplication.run(SpeakApp.class, args);
    }
}

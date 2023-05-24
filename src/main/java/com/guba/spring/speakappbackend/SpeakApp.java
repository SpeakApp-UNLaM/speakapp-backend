package com.guba.spring.speakappbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class SpeakApp {

    public static void main(String[] args) {
        log.debug("Startup app java SpeakApp");
        SpringApplication.run(SpeakApp.class, args);
    }
}

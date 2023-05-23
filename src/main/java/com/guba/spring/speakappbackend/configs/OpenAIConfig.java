package com.guba.spring.speakappbackend.configs;


import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@ConfigurationProperties("speak-backend.client.openai")
@Indexed
@Data
@Slf4j
public class OpenAIConfig {

    public static final String BEARER = "Bearer ";

    @Value("${speak-backend.client.openai.http-client.read-timeout-ms}")
    private int readTimeoutMs;
    @Value("${speak-backend.client.openai.http-client.connect-timeout-ms}")
    private int connectTimeoutMs;
    @Value("${speak-backend.client.openai.api-key}")
    private String apiKey;
    @Value("${speak-backend.client.openai.gpt-model}")
    private String gptModel;
    @Value("${speak-backend.client.openai.audio-model}")
    private String audioModel;

    @Bean
    public Request.Options options() {
        return new Request.Options(getConnectTimeoutMs(), MILLISECONDS,  getReadTimeoutMs(), MILLISECONDS, true);
    }

    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return request -> request.header(AUTHORIZATION, BEARER + apiKey);
    }
}
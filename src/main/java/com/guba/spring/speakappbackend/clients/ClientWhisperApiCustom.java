package com.guba.spring.speakappbackend.clients;

import com.guba.spring.speakappbackend.web.schemas.TranscriptionResultDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
@ConfigurationProperties("speak-backend.client.whisper")
@Setter
@Getter
public class ClientWhisperApiCustom {

    private RestTemplate restTemplate;

    private String url;
    private String apiKey;
    private MultiValueMap<String, String> headers;
    private Duration connectTimeout;
    private Duration readTimeout;


    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(connectTimeout)
                .setReadTimeout(readTimeout)
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().addAll(headers);
                    request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
                    return execution.execute(request, body);
                })
                .build();
    }

    public TranscriptionResultDTO getTranscription(Resource resourceData) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", "whisper-1");
        body.add("file", resourceData);
        body.add("language", "es");
        body.add("temperature", 0);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, requestEntity,  TranscriptionResultDTO.class);
    }

}

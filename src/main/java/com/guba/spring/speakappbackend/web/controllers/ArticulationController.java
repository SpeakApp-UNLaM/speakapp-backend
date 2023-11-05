package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.ArticulationService;
import com.guba.spring.speakappbackend.services.VideoStreamService;
import com.guba.spring.speakappbackend.web.schemas.ArticulationDTO;
import com.guba.spring.speakappbackend.web.schemas.PhonemeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "articulations")
@Slf4j
public class ArticulationController {

    private final VideoStreamService videoStreamService;
    private final ArticulationService articulationService;


    @GetMapping(value = "/video/input-stream-resource/{title}", produces = "video/mp4")
    public ResponseEntity<InputStreamResource> retrieveResource(@PathVariable final String title) throws Exception {
        var body = this.videoStreamService.getVideoHowFileInputStream("toystory.mp4");
        long sizeFile = body.getChannel().size();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .header(HttpHeaders.CONTENT_RANGE, "bytes 0-1023/" + sizeFile)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(sizeFile))
                .body(new InputStreamResource(body));
    }

    @GetMapping(value = "/video/byte/{title}", produces = "video/mp4")
    public ResponseEntity<byte[]> getVideoHowByte(
            ServerHttpResponse serverHttpResponse,
            @RequestHeader(value = "Range", required = false) String range,
            @PathVariable final String title) {
        return this.videoStreamService.prepareContent("toystory", "mp4", range);
    }

    @GetMapping(value = "/video/resource/{title}", produces = "video/mp4")
    public ResponseEntity<Resource> getVideoHowResource(@PathVariable final String title) {
        var body = this.videoStreamService.getVideoHowResource("toystory.mp4");
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(body);
    }

    @GetMapping(path = "/available")
    public ResponseEntity<List<PhonemeDTO>> getArticulationsAvailable() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.articulationService.getArticulationsAvailable());
    }

    @GetMapping(path = "/{idPhoneme}")
    public ResponseEntity<ArticulationDTO> getArticulation(@PathVariable final Long idPhoneme) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.articulationService.getArticulation(idPhoneme));
    }
}

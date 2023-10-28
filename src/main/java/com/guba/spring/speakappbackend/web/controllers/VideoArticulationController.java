package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.VideoStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "video-articulation")
@Slf4j
public class VideoArticulationController {

    private final VideoStreamService videoStreamService;

    @GetMapping(value = "input-stream-resource/{title}", produces = "video/mp4")
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

    @GetMapping(value = "byte/{title}", produces = "video/mp4")
    public ResponseEntity<byte[]> getVideoHowByte(
            ServerHttpResponse serverHttpResponse,
            @RequestHeader(value = "Range", required = false) String range,
            @PathVariable final String title) {
        return this.videoStreamService.prepareContent("toystory", "mp4", range);
    }

    @GetMapping(value = "resource/{title}", produces = "video/mp4")
    public ResponseEntity<Resource> getVideoHowResource(@PathVariable final String title) {
        var body = this.videoStreamService.getVideoHowResource("toystory.mp4");
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(body);
    }
}

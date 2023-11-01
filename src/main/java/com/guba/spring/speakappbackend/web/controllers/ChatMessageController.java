package com.guba.spring.speakappbackend.web.controllers;

import com.guba.spring.speakappbackend.services.ChatMessageService;
import com.guba.spring.speakappbackend.web.schemas.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "chat-messages")
@Slf4j
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<List<ChatMessageDTO>> createMessage(
            @RequestBody @Valid ChatMessageDTO chatMessageDTO,
            @RequestParam(name = "limit", defaultValue = "20" ) int limit) {
        log.info("create message, limit {}", limit);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.chatMessageService.create(chatMessageDTO, limit));
    }

    @GetMapping(path = "/{idUser}")
    public ResponseEntity<List<ChatMessageDTO>> getMessages(
            @PathVariable(name = "idUser") Long idToUser,
            @RequestParam(name = "limit", defaultValue = "20" ) int limit) {
        log.info("get message to user {}, limit {}", idToUser, 20);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.chatMessageService.getMessages(idToUser, limit));
    }
}

package com3.demo.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final AmqpTemplate amqpTemplate;

    @Getter
    public static class Message {
        String contents;
    }

    @PostMapping(value = "/api/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postMessage(@RequestBody Message message) {
        amqpTemplate.convertAndSend("miristudy-direct-exchange", "test.routing", message.getContents());
        return ResponseEntity.ok(message.getContents());
    }
}

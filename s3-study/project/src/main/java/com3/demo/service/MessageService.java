package com3.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

    @RabbitListener(queues = "one")
    public void handleMessage(String message) {
        log.info("handleMessageOne: {}", message);
    }

    @RabbitListener(queues = "two")
    public void handleMessageTwo(String message) {
        log.info("handleMessageTwo: {}", message);
    }
}

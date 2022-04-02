package com.folksdev.folksdevbank.controller;

import com.folksdev.folksdevbank.model.Message;
import com.folksdev.folksdevbank.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> receiveMessages(){
        return ResponseEntity.ok(messageService.messages());
    }

}

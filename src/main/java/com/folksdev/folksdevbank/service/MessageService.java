package com.folksdev.folksdevbank.service;

import com.folksdev.folksdevbank.model.Message;
import com.folksdev.folksdevbank.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final AmqpTemplate rabbitTemplate;
    private final MessageRepository messageRepository;

    public List<Message> messages(){
        return messageRepository.findAll();
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void receiveMessages(String message) {
        messageRepository.save(new Message(null, message));
    }

    public void sendRabbitMessage(String message){
        rabbitTemplate.convertAndSend("sample.example", "sample.routingKey", message);
    }

}

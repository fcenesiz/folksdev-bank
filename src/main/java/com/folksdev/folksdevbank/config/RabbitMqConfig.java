package com.folksdev.folksdevbank.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${sample.rabbitmq.exchange}")
    String exchange;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue queue(){
        return new Queue(queueName, true);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}

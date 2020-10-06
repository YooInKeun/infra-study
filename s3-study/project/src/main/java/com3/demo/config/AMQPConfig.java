package com3.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AMQPConfig {

    public static final String ROUTING_KEY = "test.routing";

    @Bean
    public Queue queueOne() {
        return QueueBuilder
                .durable("one")
                .build();
    }

    @Bean
    public Queue queueTwo() {
        return QueueBuilder
                .durable("two")
                .build();
    }

    @Bean
    public Exchange directExchange() {
        return new DirectExchange("miristudy-direct-exchange");
    }

    @Bean
    public Binding binding(Exchange exchange, Queue queueOne) {
        return BindingBuilder.bind(queueOne)
                .to(exchange)
                .with("test.routing")
                .noargs();
    }

    // 송신자 설정
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        return rabbitTemplate;
    }

    // 수신자 설정
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        return messageListenerContainer;
    }

}

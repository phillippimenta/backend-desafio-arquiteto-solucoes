package br.com.phillippimenta.desafio.leadmagnet.config.messaging;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Slf4j
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchangeName;
    @Value("${rabbitmq.routing-key}")
    private String routingKey;
    public static final String QUEUE_NAME = "lead.created.queue";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public DirectExchange leadExchange() {
        log.info("Declarando exchange...");
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue leadQueue() {
        log.info("Declarando fila...");
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding leadBinding(Queue leadQueue, DirectExchange leadExchange) {
        log.info("Declarando binding...");
        return BindingBuilder.bind(leadQueue).to(leadExchange).with(routingKey);
    }
}

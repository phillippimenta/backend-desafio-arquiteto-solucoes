package br.com.phillippimenta.desafio.leadmagnet.config;

import lombok.Getter;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    private static final String QUEUE_NAME = "lead.created.queue";

    @Bean
    public DirectExchange leadExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue leadQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding leadBinding(Queue leadQueue, DirectExchange leadExchange) {
        return BindingBuilder.bind(leadQueue)
                .to(leadExchange)
                .with(routingKey);
    }
}

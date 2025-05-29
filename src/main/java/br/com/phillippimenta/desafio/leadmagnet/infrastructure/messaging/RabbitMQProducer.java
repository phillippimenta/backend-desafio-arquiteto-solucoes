package br.com.phillippimenta.desafio.leadmagnet.infrastructure.messaging;

import br.com.phillippimenta.desafio.leadmagnet.domain.Lead;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Lead lead) {
        rabbitTemplate.convertAndSend(exchange, routingKey, lead);
    }
}

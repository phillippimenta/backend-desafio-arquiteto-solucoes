package br.com.phillippimenta.desafio.leadmagnet.infrastructure.messaging;

import br.com.phillippimenta.desafio.leadmagnet.domain.Lead;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class RabbitMQProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;
    @InjectMocks
    private RabbitMQProducer producer;

    @BeforeEach
    void setUp() {
        openMocks(this);
        // Simula as propriedades do @Value manualmente
        producer = new RabbitMQProducer(rabbitTemplate);
        // Simula injeção das propriedades do Spring
        ReflectionTestUtils.setField(producer, "exchange", "lead.exchange");
        ReflectionTestUtils.setField(producer, "routingKey", "lead.routing-key");
    }

    @Test
    void deveEnviarMensagemComRabbitTemplate() {
        // Arrange
        Lead lead = new Lead();
        lead.setName("Maria");
        lead.setEmail("maria@email.com");
        lead.setCpf("12345678901");
        lead.setPhone("11999999999");
        // Act
        producer.sendMessage(lead);
        // Assert
        verify(rabbitTemplate, times(1))
                .convertAndSend("lead.exchange", "lead.routing-key", lead);
    }
}

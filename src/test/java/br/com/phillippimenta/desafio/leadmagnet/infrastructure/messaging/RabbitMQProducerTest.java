package br.com.phillippimenta.desafio.leadmagnet.infrastructure.messaging;

import br.com.phillippimenta.desafio.leadmagnet.domain.Lead;
import br.com.phillippimenta.desafio.leadmagnet.domain.LeadNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
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
        producer = new RabbitMQProducer(rabbitTemplate);
        ReflectionTestUtils.setField(producer, "exchange", "lead.exchange");
        ReflectionTestUtils.setField(producer, "routingKey", "lead.routing-key");
    }

    @Test
    void deveEnviarMensagemSanitizadaComRabbitTemplate() {
        // Arrange
        Lead lead = new Lead();
        lead.setName("Maria");
        lead.setEmail("maria@email.com");
        lead.setCpf("12345678901");
        lead.setPhone("11999999999");
        lead.setCreatedAt(LocalDateTime.now());
        // Act
        producer.sendMessage(lead);
        // Assert
        ArgumentCaptor<LeadNotification> captor = ArgumentCaptor.forClass(LeadNotification.class);
        verify(rabbitTemplate, times(1))
                .convertAndSend(eq("lead.exchange"), eq("lead.routing-key"), captor.capture());
        LeadNotification notification = captor.getValue();
        assertThat(notification).isNotNull();
        assertThat(notification.getName()).isEqualTo("Maria");
        assertThat(notification.getCreatedAt()).isEqualTo(lead.getCreatedAt());
    }
}
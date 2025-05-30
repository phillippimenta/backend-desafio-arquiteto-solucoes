package br.com.phillippimenta.desafio.leadmagnet.application.service;

import br.com.phillippimenta.desafio.leadmagnet.application.dto.LeadRequest;
import br.com.phillippimenta.desafio.leadmagnet.domain.Lead;
import br.com.phillippimenta.desafio.leadmagnet.domain.LeadAlreadyExistsException;
import br.com.phillippimenta.desafio.leadmagnet.infrastructure.messaging.RabbitMQProducer;
import br.com.phillippimenta.desafio.leadmagnet.infrastructure.persistence.LeadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeadServiceTest {

    @Mock
    private LeadRepository repository;
    @Mock
    private RabbitMQProducer producer;
    @InjectMocks
    private LeadService service;
    private LeadRequest leadRequest;

    @BeforeEach
    void setUp() {
        leadRequest = new LeadRequest();
        leadRequest.setName("João da Silva");
        leadRequest.setEmail("joao@email.com");
        leadRequest.setCpf("12345678901");
        leadRequest.setPhone("11999999999");
    }

    @Test
    void deveRegistrarLeadComSucesso() {
        // Arrange
        when(repository.existsByEmail(leadRequest.getEmail())).thenReturn(false);
        when(repository.existsByCpf(leadRequest.getCpf())).thenReturn(false);
        // Act
        service.register(leadRequest);
        // Assert
        ArgumentCaptor<Lead> leadCaptor = ArgumentCaptor.forClass(Lead.class);
        verify(repository, times(1)).save(leadCaptor.capture());
        verify(producer, times(1)).sendMessage(any());
        Lead leadSalvo = leadCaptor.getValue();
        assertEquals(leadRequest.getName(), leadSalvo.getName());
        assertEquals(leadRequest.getEmail(), leadSalvo.getEmail());
        assertEquals(leadRequest.getCpf(), leadSalvo.getCpf());
        assertEquals(leadRequest.getPhone(), leadSalvo.getPhone());
        assertNotNull(leadSalvo.getCreatedAt());
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        // Arrange
        when(repository.existsByEmail(leadRequest.getEmail())).thenReturn(true);
        // Act
        LeadAlreadyExistsException ex = assertThrows(LeadAlreadyExistsException.class, () -> {
            service.register(leadRequest);
        });
        // Assert
        assertEquals("E-mail já cadastrado.", ex.getMessage());
        verify(repository, never()).save(any());
        verify(producer, never()).sendMessage(any());
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {
        // Arrange
        when(repository.existsByEmail(leadRequest.getEmail())).thenReturn(false);
        when(repository.existsByCpf(leadRequest.getCpf())).thenReturn(true);
        // Act
        LeadAlreadyExistsException ex = assertThrows(LeadAlreadyExistsException.class, () -> {
            service.register(leadRequest);
        });
        // Assert
        assertEquals("CPF já cadastrado.", ex.getMessage());
        verify(repository, never()).save(any());
        verify(producer, never()).sendMessage(any());
    }
}

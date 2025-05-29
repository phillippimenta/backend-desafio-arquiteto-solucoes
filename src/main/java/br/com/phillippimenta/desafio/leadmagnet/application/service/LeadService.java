package br.com.phillippimenta.desafio.leadmagnet.application.service;

import br.com.phillippimenta.desafio.leadmagnet.application.dto.LeadRequest;
import br.com.phillippimenta.desafio.leadmagnet.domain.Lead;
import br.com.phillippimenta.desafio.leadmagnet.domain.exception.LeadAlreadyExistsException;
import br.com.phillippimenta.desafio.leadmagnet.infrastructure.messaging.RabbitMQProducer;
import br.com.phillippimenta.desafio.leadmagnet.infrastructure.persistence.LeadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LeadService {

    private final LeadRepository repository;
    private final RabbitMQProducer producer;

    public LeadService(LeadRepository repository, RabbitMQProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public void register(LeadRequest request) {
        validateLead(request);
        Lead lead = buildLeadFromRequest(request);
        repository.save(lead);
        producer.sendMessage(lead);
    }

    private void validateLead(LeadRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new LeadAlreadyExistsException("E-mail já cadastrado.");
        }
        if (repository.existsByCpf(request.getCpf())) {
            throw new LeadAlreadyExistsException("CPF já cadastrado.");
        }
    }

    private Lead buildLeadFromRequest(LeadRequest request) {
        Lead lead = new Lead();
        lead.setName(request.getName());
        lead.setCpf(request.getCpf());
        lead.setEmail(request.getEmail());
        lead.setPhone(request.getPhone());
        lead.setCreatedAt(LocalDateTime.now());
        return lead;
    }
}

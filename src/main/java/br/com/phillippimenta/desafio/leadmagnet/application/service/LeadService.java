package br.com.phillippimenta.desafio.leadmagnet.application.service;

import br.com.phillippimenta.desafio.leadmagnet.application.dto.LeadRequest;
import br.com.phillippimenta.desafio.leadmagnet.domain.Lead;
import br.com.phillippimenta.desafio.leadmagnet.domain.LeadAlreadyExistsException;
import br.com.phillippimenta.desafio.leadmagnet.infrastructure.messaging.RabbitMQProducer;
import br.com.phillippimenta.desafio.leadmagnet.infrastructure.persistence.LeadRepository;
import br.com.phillippimenta.desafio.leadmagnet.infrastructure.security.CryptoUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LeadService {

    private final LeadRepository repository;
    private final RabbitMQProducer producer;
    private final CryptoUtils cryptoUtils;

    public LeadService(LeadRepository repository,
                       RabbitMQProducer producer,
                       CryptoUtils cryptoUtils) {
        this.repository = repository;
        this.producer = producer;
        this.cryptoUtils = cryptoUtils;
    }

    public void register(LeadRequest request) {
        validateLead(request);
        Lead lead = buildLeadFromRequest(request);
        repository.save(lead);
        producer.sendMessage(lead);
    }

    private void validateLead(LeadRequest request) {
        if (repository.existsByEmail(this.cryptoUtils.encrypt(request.getEmail()))) {
            throw new LeadAlreadyExistsException("E-mail já cadastrado.");
        }
        if (repository.existsByCpf(this.cryptoUtils.encrypt(request.getCpf()))) {
            throw new LeadAlreadyExistsException("CPF já cadastrado.");
        }
    }

    private Lead buildLeadFromRequest(LeadRequest request) {
        Lead lead = new Lead();
        lead.setName(request.getName());
        lead.setCpf(this.cryptoUtils.encrypt(request.getCpf()));
        lead.setEmail(this.cryptoUtils.encrypt(request.getEmail()));
        lead.setPhone(this.cryptoUtils.encrypt(request.getPhone()));
        lead.setCreatedAt(LocalDateTime.now());
        lead.setConsentGiven(request.isConsentGiven());
        lead.setConsentIp(request.getConsentIp());
        return lead;
    }
}

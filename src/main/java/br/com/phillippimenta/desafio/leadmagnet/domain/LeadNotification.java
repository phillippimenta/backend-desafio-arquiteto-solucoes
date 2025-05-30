package br.com.phillippimenta.desafio.leadmagnet.domain;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class LeadNotification implements Serializable {

    private final String name;
    private final LocalDateTime createdAt;

    public LeadNotification(Lead lead) {
        this.name = lead.getName();
        this.createdAt = lead.getCreatedAt();
    }
}

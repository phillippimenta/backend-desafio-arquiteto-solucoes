package br.com.phillippimenta.desafio.leadmagnet.domain;

public class LeadAlreadyExistsException extends RuntimeException {
    public LeadAlreadyExistsException(String message) {
        super(message);
    }
}

package br.com.phillippimenta.desafio.leadmagnet.domain.exception;

public class LeadAlreadyExistsException extends RuntimeException {
    public LeadAlreadyExistsException(String message) {
        super(message);
    }
}

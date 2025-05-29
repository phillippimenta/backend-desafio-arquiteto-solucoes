package br.com.phillippimenta.desafio.leadmagnet.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class LeadRequest {
    @NotBlank
    private String name;
    @CPF
    @NotBlank
    private String cpf;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "\\d{10,11}")
    private String phone;
}

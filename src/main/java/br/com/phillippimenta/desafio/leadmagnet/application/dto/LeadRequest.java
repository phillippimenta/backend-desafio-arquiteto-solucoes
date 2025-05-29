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
    @NotBlank(message = "O nome é obrigatório.")
    private String name;
    @CPF(message = "O CPF informado é inválido.")
    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;
    @Email(message = "O e-mail informado é inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;
    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(
            regexp = "\\d{10,11}",
            message = "O telefone deve conter apenas números e ter entre 10 e 11 dígitos."
    )
    private String phone;
}

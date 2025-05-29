package br.com.phillippimenta.desafio.leadmagnet.presentation;

import br.com.phillippimenta.desafio.leadmagnet.application.dto.LeadRequest;
import br.com.phillippimenta.desafio.leadmagnet.application.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/leads")
@Tag(name = "Leads", description = "Operações para captura de leads via pré-cadastro")
@Validated
public class LeadController {

    private final LeadService service;

    public LeadController(LeadService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar um novo lead",
            description = "Endpoint utilizado para pré-cadastro de leads, coletando nome, CPF, telefone e e-mail."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lead cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos ou mal formatados)"),
            @ApiResponse(responseCode = "409", description = "CPF ou e-mail já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody LeadRequest request) {
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}


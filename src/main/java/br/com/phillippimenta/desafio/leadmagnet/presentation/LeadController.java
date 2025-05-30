package br.com.phillippimenta.desafio.leadmagnet.presentation;

import br.com.phillippimenta.desafio.leadmagnet.application.dto.LeadRequest;
import br.com.phillippimenta.desafio.leadmagnet.application.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            description = "Endpoint utilizado para pré-cadastro de leads, coletando nome, CPF, telefone, e-mail e consentimento LGPD."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lead cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos ou mal formatados)"),
            @ApiResponse(responseCode = "409", description = "CPF ou e-mail já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody LeadRequest request, HttpServletRequest httpRequest) {
        String clientIp = extractClientIp(httpRequest);
        request.setConsentIp(clientIp);
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            // Em caso de múltiplos IPs (proxy chain), pega o primeiro
            ip = ip.split(",")[0].trim();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}

package br.com.phillippimenta.desafio.leadmagnet.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pré-Cadastro de Leads")
                        .version("v1")
                        .description("Captura de leads para onboarding do novo banco.")
                        .contact(new Contact()
                                .name("Phillip Eduardo Pimenta Forte")
                                .email("phillippimenta@gmail.com")
                        )
                );
    }
}

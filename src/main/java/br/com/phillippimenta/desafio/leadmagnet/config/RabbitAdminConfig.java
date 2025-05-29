package br.com.phillippimenta.desafio.leadmagnet.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitAdminConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public CommandLineRunner debugRabbit(RabbitAdmin rabbitAdmin) {
        return args -> {
            System.out.println("Forçando criação das filas...");
            rabbitAdmin.initialize();
        };
    }
}
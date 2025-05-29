package br.com.phillippimenta.desafio.leadmagnet.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class RedisProperties {

    private String host;
    private int port;
    private boolean ssl;
}

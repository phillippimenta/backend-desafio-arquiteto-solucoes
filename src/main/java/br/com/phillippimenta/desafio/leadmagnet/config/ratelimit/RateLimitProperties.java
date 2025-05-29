package br.com.phillippimenta.desafio.leadmagnet.config.ratelimit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rate-limit")
@Getter
@Setter
public class RateLimitProperties {

    private int capacity;
    private int tokensPerPeriod;
    private int period;
}

package br.com.phillippimenta.desafio.leadmagnet.config;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
@EnableConfigurationProperties({
        RedisProperties.class,
        RateLimitProperties.class
})
public class RedisRateLimiterConfig {

    @Bean
    public RedisClient redisClient(RedisProperties properties) {
        return RedisClient.create(RedisURI.builder()
                .withHost(properties.getHost())
                .withPort(properties.getPort())
                .withSsl(properties.isSsl())
                .build());
    }

    @Bean
    public StatefulRedisConnection<String, byte[]> redisConnection(RedisClient redisClient) {
        return redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
    }

    @Bean
    public ProxyManager<String> proxyManager(StatefulRedisConnection<String, byte[]> redisConnection) {
        return LettuceBasedProxyManager.builderFor(redisConnection)
                .withExpirationStrategy(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(1))
                )
                .build();
    }

    @Bean
    public Supplier<BucketConfiguration> bucketConfiguration(RateLimitProperties properties) {
        return () -> BucketConfiguration.builder()
                .addLimit(limit -> limit
                        .capacity(properties.getCapacity())
                        .refillIntervally(properties.getTokensPerPeriod(), Duration.ofMinutes(properties.getPeriod()))
                )
                .build();
    }
}

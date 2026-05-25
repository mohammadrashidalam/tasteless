package com.alam.gatway.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Configuration
@Slf4j
public class RateLimiterConfig {
    @Bean
    public KeyResolver userKeyResolver() {
        log.info("Initializing User Key Resolver for Rate Limiting");
        return exchange -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            log.info("Extracting client IP for rate limiting from headers: {}", headers);

            String xff = headers.getFirst("X-Forwarded-For");

            // FIXED CONDITION
            if (xff != null && !xff.isBlank()) {
                return Mono.just(xff.split(",")[0].trim());
            }

            InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();

            String ip = (remoteAddress != null && remoteAddress.getAddress() != null)
                    ? remoteAddress.getAddress().getHostAddress()
                    : "unknown";
            log.info("Client IP resolved for rate limiting: {}", ip);
            return Mono.just(ip);
        };
    }
}

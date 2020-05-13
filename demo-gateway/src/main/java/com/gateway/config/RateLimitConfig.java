package com.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * gateway  redis限流配置
 * @Date 2020/5/12 15:08
 * @name RateLimitConfig
 */


@Configuration
public class RateLimitConfig {

    @Bean
   public KeyResolver userKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                System.out.println("RateLimitConfig>>>>"+exchange.getRequest().getQueryParams().get("token"));
                return Mono.just(exchange.getRequest().getQueryParams().getFirst("token"));
            }
        };
    }
}

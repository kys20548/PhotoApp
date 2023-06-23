package com.photoapp.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;


@Component
@Slf4j
public class MyPreFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("My first prefilter is executed.........");
        String path = exchange.getRequest().getPath().value();
        log.info(path);
        HttpHeaders headers =  exchange.getRequest().getHeaders();
        Set<String> entries = exchange.getRequest().getHeaders().keySet();
        entries.forEach(x->{
            log.info(x+" "+headers.getFirst(x));
        });
        return chain.filter(exchange);
    }
}

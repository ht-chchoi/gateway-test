package com.example.gatewaytest.webfilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

  @Override
  public GatewayFilter apply(final Config config) {
    return (((exchange, chain) -> {
      if (!exchange.getRequest().getMethod().matches("GET")){
        chain.filter(exchange);
      }
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }));
  }

  public static class Config {

  }
}

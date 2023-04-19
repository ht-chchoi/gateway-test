package com.example.gatewaytest.router;

import com.example.gatewaytest.webfilter.AuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ProxyRouter {
  @Value("${proxy.target.scheme}")
  private String targetScheme;
  @Value("${proxy.target.host}")
  private String targetHost;
  @Value("${proxy.target.port}")
  private String targetPort;
  @Value("${proxy.header.removeList}")
  private List<String> headerRemoveList;

  private final AuthFilter authFilter;

  @Bean
  public RouteLocator proxyRouteFuc(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder.routes()
        .route("all", r -> r
            .predicate(serverWebExchange -> {
              log.info("qwe");
              if (serverWebExchange.getRequest().getMethod().matches("GET")) {
                return true;
              }
              return true;
            })
            .filters(gatewayFilterSpec -> gatewayFilterSpec
                .filter(authFilter.apply(new AuthFilter.Config())))
            .uri(targetScheme + "://" + targetHost + ":" + targetPort))
        .build();
  }
}

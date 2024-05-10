package com.pre_order.api_gateway.filter;

import com.pre_order.api_gateway.jwt.TokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(AuthorizationHeaderFilter.Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Request Header에 토큰이 존재하지 않을 때
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("AuthorizationHeaderFilter Token is not exist");
                return handleUnAuthorized(exchange);
            }

            // Request Header에서 토큰 문자열 받아오기
            String authorization =
                    Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();
            String token = authorization.replace("Bearer", "").trim();
            String userId = TokenValidator.getUserId(token);

            // 로그아웃한 토큰인지 확인

            // 토큰 검증
            if (!TokenValidator.validateToken(token)) {
                log.warn("AuthorizationHeaderFilter Token is not valid");
                return handleUnAuthorized(exchange);
            }

            request = request.mutate()
                    .header("X-USER-ID", userId)
                    .build();

            log.info("RequestHeader: {}", request.getHeaders());

            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        });
    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
        log.warn("Handling Unauthorized request");

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
    }

    public static class Config {}
}

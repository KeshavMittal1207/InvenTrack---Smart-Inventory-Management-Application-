    package com.smartinventorymanagement.ApiGateway_Service.Filter;

    import com.smartinventorymanagement.ApiGateway_Service.Util.JwtUtil;
    import lombok.RequiredArgsConstructor;
    import org.springframework.cloud.gateway.filter.GatewayFilterChain;
    import org.springframework.cloud.gateway.filter.GlobalFilter;
    import org.springframework.core.Ordered;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.server.reactive.ServerHttpRequest;
    import org.springframework.stereotype.Component;
    import org.springframework.web.server.ServerWebExchange;
    import reactor.core.publisher.Mono;

    @Component
    @RequiredArgsConstructor
    public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

        private final JwtUtil jwtUtil;

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();
            System.out.println("Request path: " + request.getURI().getPath());

            if (request.getURI().getPath().matches("^/auth/(login|register)/?$")) {
                return chain.filter(exchange);
            }
            // Public endpoints

            if (!request.getHeaders().containsKey("Authorization")) {
                System.out.println("No Authorization header.");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = request.getHeaders().getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("Invalid Authorization header.");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.isTokenValid(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String username = jwtUtil.getClaims(token).getSubject();

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Name", username)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }

        @Override
        public int getOrder() {
            return -1;
        }
    }
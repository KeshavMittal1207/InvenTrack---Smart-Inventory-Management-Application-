package com.smartinventorymanagement.ApiGateway_Service.Routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("Alert-Service",r -> r.path("/alert/**")
                        .uri("lb://ALERT-SERVICE"))
                .route("Inventory-Service",r -> r.path("/inventory/**")
                        .uri("lb://INVENTORY-SERVICE"))
                .route("Order-Service",r -> r.path("/order/**")
                        .uri("lb://ORDER-SERVICE"))
                .route("Seller-Service",r -> r.path("/seller/**")
                        .uri("lb://SELLER-SERVICE"))
                .route("AUTH-SERVICE",r -> r.path("/auth/**")
                        .uri("http://localhost:8087"))
                .build();
    }
}

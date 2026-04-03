package com.smartinventorymanagement.ApiGateway_Service.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.smartinventorymanagement.ApiGateway_Service.Security.JwtAuthenticationFilter;
import com.smartinventorymanagement.ApiGateway_Service.Security.JwtUtil;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtUtil);
    }
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http , JwtAuthenticationFilter jwtAuthenticationFilter){
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/inventory/**").hasAnyRole("STAFF" , "SHOP_OWNER")
                .pathMatchers("/order/**").hasAnyRole("STAFF" , "SHOP_OWNER")
                .pathMatchers("/seller/**").hasAnyRole("STAFF" , "SHOP_OWNER")
                .pathMatchers("/alert/**").hasAnyRole("STAFF" , "SHOP_OWNER")
                .pathMatchers("/product/**").hasAnyRole("STAFF" , "SHOP_OWNER")
                .pathMatchers("/dashboard/**").hasAnyRole("STAFF" , "SHOP_OWNER")
                .anyExchange().authenticated())
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}

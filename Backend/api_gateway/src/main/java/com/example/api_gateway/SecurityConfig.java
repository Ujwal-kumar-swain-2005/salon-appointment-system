package com.example.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFlux
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.authorizeExchange(exchanges -> exchanges
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/api/notifications/ws/**").permitAll()
                .pathMatchers("/api/salons/**",
                        "/api/categories/**",
                        "/api/notifications/**",
                        "/api/payments/**",
                        "/api/bookings/**",
                        "/api/users/**",
                        "/api/service-offering/**",
                        "/api/reviews/**")
                .hasAnyRole("CUSTOMER", "SALON_OWNER", "ADMIN")

                .pathMatchers("/api/categories/salon-owner/**",
                        "/api/service-offering/salon-owner/**",
                        "/api/notifications/salon-owner")
                .hasRole("SALON_OWNER")

                .anyExchange().authenticated()
        );

        http.oauth2ResourceServer(oauth ->
                oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
        );

        return http.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}

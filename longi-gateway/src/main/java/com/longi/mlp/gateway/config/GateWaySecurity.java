package com.longi.mlp.gateway.config;

import com.longi.mlp.gateway.config.jwt.JwtOAuth2AuthenticationToken;
import com.longi.mlp.gateway.filter.AuthcFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 网关Security安全验证
 */
@Configuration
@EnableWebFluxSecurity
@Slf4j
@RequiredArgsConstructor
public class GateWaySecurity {
    private final ReactiveJwtDecoder reactiveJwtDecoder;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {
        http.headers().frameOptions().disable();
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeExchange()
                .pathMatchers().permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(new AuthcFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .oauth2ResourceServer().jwt()
                .jwtDecoder(reactiveJwtDecoder)
                .jwtAuthenticationConverter(new JwtOAuth2AuthenticationToken());
        return http.build();
    }
}
